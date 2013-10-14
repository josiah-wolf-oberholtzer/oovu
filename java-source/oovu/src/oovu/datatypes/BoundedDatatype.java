package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.AttributeServer;
import oovu.timing.EnvelopeHandler;
import oovu.timing.MultiEnvelope;

import com.cycling74.max.Atom;

abstract public class BoundedDatatype extends Datatype implements
    EnvelopeHandler {

    private class GetMaximumMessageHandler extends DatatypeMessageHandler {

        public GetMaximumMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getmaximum";
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
            Atom[][] result = new Atom[1][];
            Double maximum = BoundedDatatype.this.get_maximum();
            String label = "maximum";
            if (maximum != null) {
                result[0] = Atom.newAtom(label, Atom.newAtom(new double[] {
                    maximum
                }));
            } else {
                result[0] = Atom.newAtom(new String[] {
                    label
                });
            }
            return result;
        }
    }

    private class GetMinimumMessageHandler extends DatatypeMessageHandler {

        public GetMinimumMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getminimum";
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
            Atom[][] result = new Atom[1][];
            Double minimum = BoundedDatatype.this.get_minimum();
            String label = "minimum";
            if (minimum != null) {
                result[0] = Atom.newAtom(label, Atom.newAtom(new double[] {
                    minimum
                }));
            } else {
                result[0] = Atom.newAtom(new String[] {
                    label
                });
            }
            return result;
        }
    }

    private class SetMaximumMessageHandler extends DatatypeMessageHandler {

        public SetMaximumMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public void call_after() {
            this.attribute_server.reoutput_value();
        }

        @Override
        public String get_name() {
            return "maximum";
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
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

    private class SetMinimumMessageHandler extends DatatypeMessageHandler {

        public SetMinimumMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public void call_after() {
            this.attribute_server.reoutput_value();
        }

        @Override
        public String get_name() {
            return "minimum";
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
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

    public BoundedDatatype(AttributeServer client,
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
