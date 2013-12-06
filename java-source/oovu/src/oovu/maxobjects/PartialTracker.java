package oovu.maxobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oovu.adapters.GenericMaxAdapter;
import oovu.clients.MaxPeer;
import oovu.messaging.Atoms;
import oovu.messaging.Request;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

public class PartialTracker extends MaxPeer {
    private class Partial implements Comparable<Partial> {
        public final Double frequency;
        public final Double amplitude;

        public Partial(double frequency, double amplitude) {
            this.frequency = frequency;
            this.amplitude = amplitude;
        }

        @Override
        public int compareTo(Partial expr) {
            int comparison = this.amplitude.compareTo(expr.amplitude);
            return comparison;
        }

        @Override
        public String toString() {
            return "Partial ["
                + (this.frequency != null ? "frequency=" + this.frequency
                    + ", " : "")
                + (this.amplitude != null ? "amplitude=" + this.amplitude : "")
                + "]";
        }
    }

    private List<Partial> partials = new ArrayList<Partial>();

    public PartialTracker(Atom[] arguments) {
        this.declareIO(1, 1);
        this.max_adapter = new GenericMaxAdapter(this);
    }

    @Override
    public void handle_request(Request request) {
        if (request.payload.length < 2) {
            return;
        }
        List<Partial> new_partials = new ArrayList<Partial>();
        for (int i = 0, j = request.payload.length; (i + 1) < j; i += 2) {
            Double frequency = request.payload[i].toDouble();
            Double amplitude = request.payload[i + 1].toDouble();
            Partial new_partial = new Partial(frequency, amplitude);
            new_partials.add(new_partial);
            // Environment.log(new_partial.toString());
        }
        Collections.sort(new_partials);
        Collections.reverse(new_partials);
        new_partials = new_partials.subList(0, 32);
        if (new_partials.size() != this.partials.size()) {
            this.partials = new ArrayList<Partial>(new_partials);
        } else {
            List<Partial> prev_partials = new ArrayList<Partial>(this.partials);
            Collections.sort(prev_partials);
            Collections.reverse(prev_partials);
            List<Partial> next_partials = new ArrayList<Partial>(this.partials);
            for (Partial old_partial : prev_partials) {
                Double best_distance = null;
                Partial best_new_partial = null;
                for (Partial new_partial : new_partials) {
                    if (best_distance == null) {
                        best_distance =
                            Math.abs(old_partial.frequency
                                - new_partial.frequency);
                        best_new_partial = new_partial;
                    } else {
                        double this_distance =
                            Math.abs(old_partial.frequency
                                - new_partial.frequency);
                        if (this_distance < best_distance) {
                            best_distance = this_distance;
                            best_new_partial = new_partial;
                        }
                    }
                }
                int index = this.partials.indexOf(old_partial);
                next_partials.set(index, best_new_partial);
                new_partials.remove(best_new_partial);
            }
            this.partials = new ArrayList<Partial>(next_partials);
        }
        double[] frequencies = new double[this.partials.size()];
        for (int i = 0; i < this.partials.size(); i++) {
            frequencies[i] = this.partials.get(i).frequency;
        }
        Response response =
            new Response(null, Atoms.to_atoms("value", frequencies), request);
        this.handle_response(response);
    }
}
