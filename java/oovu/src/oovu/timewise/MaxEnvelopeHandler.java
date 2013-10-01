package oovu.timewise;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class MaxEnvelopeHandler extends MaxObject implements EnvelopeHandler {

    public MultiEnvelope multi_envelope;

    public MaxEnvelopeHandler(Atom[] arguments) {
        if (arguments.length == 0) {
            arguments = Atom.newAtom(new double[] {
                0.
            });
        }
        double[] initial_values = Atom.toDouble(arguments);
        this.multi_envelope = new MultiEnvelope(this, initial_values);
    }

    @Override
    public void bang() {
        double current_time = System.currentTimeMillis();
        double[] current_values = this.multi_envelope
            .get_current_values(current_time);
        this.outlet(0, current_values);
    }

    @Override
    public void handle_envelope_response(double[] response) {
        this.outlet(0, response);
    }

    @Override
    public void inlet(float value) {
        double[] control_values = new double[] {
            value
        };
        this.outlet(0,
            this.multi_envelope.control_all_envelopes(control_values));
    }

    @Override
    public void inlet(int value) {
        double[] control_values = new double[] {
            value
        };
        this.outlet(0,
            this.multi_envelope.control_all_envelopes(control_values));
    }

    @Override
    public void list(Atom[] values) {
        double[] control_values = Atom.toDouble(values);
        this.outlet(0,
            this.multi_envelope.control_all_envelopes(control_values));
    }

    @Override
    public void notifyDeleted() {
        this.multi_envelope.cleanup_resources();
    }
}
