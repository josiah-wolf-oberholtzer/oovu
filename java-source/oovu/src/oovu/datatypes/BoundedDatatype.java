package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.GetterMessageHandler;
import oovu.messaging.SetterMessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;
import oovu.timing.EnvelopeHandler;
import oovu.timing.MultiEnvelope;

import com.cycling74.max.Atom;

abstract public class BoundedDatatype extends Datatype implements
    EnvelopeHandler {
    private class GetMaximumMessageHandler extends GetterMessageHandler {
        public GetMaximumMessageHandler(Server client) {
            super(client, "getmaximum");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            return Atoms
                .to_atoms("maximum", BoundedDatatype.this.get_maximum());
        }
    }

    private class GetMinimumMessageHandler extends GetterMessageHandler {
        public GetMinimumMessageHandler(Server client) {
            super(client, "getminimum");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            return Atoms
                .to_atoms("minimum", BoundedDatatype.this.get_minimum());
        }
    }

    private class SetMaximumMessageHandler extends SetterMessageHandler {
        public SetMaximumMessageHandler(Server client) {
            super(client, "maximum");
        }

        @Override
        public void call_after() {
            BoundedDatatype.this.client.reoutput_value();
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Double maximum = null;
            if (0 < arguments.length) {
                maximum = arguments[0].toDouble();
            }
            BoundedDatatype.this.set_maximum(maximum);
            return null;
        }
    }

    private class SetMinimumMessageHandler extends SetterMessageHandler {
        public SetMinimumMessageHandler(Server client) {
            super(client, "minimum");
        }

        @Override
        public void call_after() {
            BoundedDatatype.this.client.reoutput_value();
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Double minimum = null;
            if (0 < arguments.length) {
                minimum = arguments[0].toDouble();
            }
            BoundedDatatype.this.set_minimum(minimum);
            return null;
        }
    }

    protected Double minimum;
    protected Double maximum;
    protected MultiEnvelope multi_envelope;

    public BoundedDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new GetMaximumMessageHandler(
                this.client));
            this.client.add_message_handler(new GetMinimumMessageHandler(
                this.client));
            this.client.add_message_handler(new SetMaximumMessageHandler(
                this.client));
            this.client.add_message_handler(new SetMinimumMessageHandler(
                this.client));
        }
    }

    protected double[] bound_doubles(double[] doubles) {
        return this.bound_doubles_by_minimum(this
            .bound_doubles_by_maximum(doubles));
    }

    protected double[] bound_doubles_by_maximum(double[] doubles) {
        if (this.maximum == null) {
            return doubles;
        }
        for (int i = 0, j = doubles.length; i < j; i++) {
            if (this.maximum < doubles[i]) {
                doubles[i] = this.maximum;
            }
        }
        return doubles;
    }

    protected double[] bound_doubles_by_minimum(double[] doubles) {
        if (this.minimum == null) {
            return doubles;
        }
        for (int i = 0, j = doubles.length; i < j; i++) {
            if (doubles[i] < this.minimum) {
                doubles[i] = this.minimum;
            }
        }
        return doubles;
    }

    @Override
    public void cleanup_resources() {
        this.multi_envelope.cleanup_resources();
    }

    protected double[] extract_bounded_doubles_from_atoms(Atom[] atoms) {
        double[] doubles = this.extract_doubles_from_atoms(atoms);
        return this.bound_doubles(doubles);
    }

    protected double[] extract_doubles_from_atoms(Atom[] atoms) {
        return Atom.toDouble(atoms);
    }

    public Double get_maximum() {
        return this.maximum;
    }

    public Double get_minimum() {
        return this.minimum;
    }

    @Override
    public void handle_envelope_response(double[] response) {
        Atom[] value = Atom.newAtom(response);
        this.value = value;
        this.client.handle_asynchronous_datatype_value_output(value);
    }

    protected void initialize_extrema(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("minimum")) {
            this.set_minimum(this.extract_doubles_from_atoms(argument_map
                .get("minimum"))[0]);
        } else {
            this.minimum = null;
        }
        if (argument_map.containsKey("maximum")) {
            this.set_maximum(argument_map.get("maximum")[0].toDouble());
        } else {
            this.maximum = null;
        }
        if (argument_map.containsKey("range")) {
            Atom[] range = argument_map.get("range");
            if (range.length == 2) {
                if (range[0].isString() && range[0].getString().equals("NULL")) {
                    this.minimum = null;
                } else {
                    this.minimum = range[0].toDouble();
                }
                if (range[1].isString() && range[1].getString().equals("NULL")) {
                    this.maximum = null;
                } else {
                    this.maximum = range[1].toDouble();
                }
            }
        }
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
        this.initialize_extrema(argument_map);
    }

    @Override
    public boolean is_binding_relevant() {
        return true;
    }

    @Override
    public boolean is_rampable() {
        return true;
    }

    public void set_maximum(Double maximum) {
        this.maximum = maximum;
        this.sort_extrema();
    }

    public void set_minimum(Double minimum) {
        this.minimum = minimum;
        this.sort_extrema();
    }

    protected void sort_extrema() {
        if ((this.minimum != null) && (this.maximum != null)) {
            Double[] extrema = new Double[] {
                this.minimum, this.maximum
            };
            Arrays.sort(extrema);
            this.minimum = extrema[0];
            this.maximum = extrema[1];
        }
    }
}
