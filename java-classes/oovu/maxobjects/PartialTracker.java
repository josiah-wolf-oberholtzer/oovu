package oovu.maxobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oovu.clients.MaxPeer;
import oovu.maxadapters.GenericMaxAdapter;
import oovu.messaging.MaxIO;
import oovu.messaging.Request;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

public class PartialTracker extends MaxPeer {
    private class Peak implements Comparable<Peak> {
        public final Double frequency;
        public final Double amplitude;

        public Peak(double frequency, double amplitude) {
            this.frequency = frequency;
            this.amplitude = amplitude;
        }

        @Override
        public int compareTo(Peak expr) {
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

    private List<Peak> partials = new ArrayList<Peak>();

    public PartialTracker(Atom[] arguments) {
        this.declareIO(1, 1);
        this.max_adapter = new GenericMaxAdapter(this);
    }

    @Override
    public void handle_request(Request request) {
        if (request.payload.length < 2) {
            return;
        }
        List<Peak> new_partials = new ArrayList<Peak>();
        for (int i = 0, j = request.payload.length; (i + 1) < j; i += 2) {
            Double frequency = request.payload[i].toDouble();
            Double amplitude = request.payload[i + 1].toDouble();
            Peak new_partial = new Peak(frequency, amplitude);
            new_partials.add(new_partial);
            // Environment.log(new_partial.toString());
        }
        Collections.sort(new_partials);
        Collections.reverse(new_partials);
        new_partials = new_partials.subList(0, 32);
        if (new_partials.size() != this.partials.size()) {
            this.partials = new ArrayList<Peak>(new_partials);
        } else {
            List<Peak> prev_partials = new ArrayList<Peak>(this.partials);
            Collections.sort(prev_partials);
            Collections.reverse(prev_partials);
            List<Peak> next_partials = new ArrayList<Peak>(this.partials);
            for (Peak old_partial : prev_partials) {
                Double best_distance = null;
                Peak best_new_partial = null;
                for (Peak new_partial : new_partials) {
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
            this.partials = new ArrayList<Peak>(next_partials);
        }
        double[] frequencies = new double[this.partials.size()];
        for (int i = 0; i < this.partials.size(); i++) {
            frequencies[i] = this.partials.get(i).frequency;
        }
        Response response =
            new Response(null, MaxIO.to_atoms("value", frequencies), request);
        this.handle_response(response);
    }
}
