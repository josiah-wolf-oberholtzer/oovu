package oovu.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

abstract public class BoundedDatatype extends Datatype {

    private class GetMaximumMessageHandler extends DatatypeMessageHandler {

        public GetMaximumMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getmaximum";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            Float maximum = BoundedDatatype.this.get_maximum();
            String label = "maximum";
            if (maximum != null) {
                result[0] = Atom.newAtom(label, Atom.newAtom(new float[] {
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
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            Float minimum = BoundedDatatype.this.get_minimum();
            String label = "minimum";
            if (minimum != null) {
                result[0] = Atom.newAtom(label, Atom.newAtom(new float[] {
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
        public Atom[][] run(Atom[] arguments) {
            Float maximum = null;
            if (0 < arguments.length) {
                maximum = arguments[0].getFloat();
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
        public Atom[][] run(Atom[] arguments) {
            Float minimum = null;
            if (0 < arguments.length) {
                minimum = arguments[0].getFloat();
            }
            BoundedDatatype.this.set_minimum(minimum);
            return null;
        }
    }

    protected Float minimum;
    protected Float maximum;

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

    protected Float[] extract_bounded_floats_from_atoms(Atom[] atoms) {
        Float[] floats = this.extract_floats_from_atoms(atoms);
        return this.minimum_bound_floats(this.maximum_bound_floats(floats));
    }

    protected Float[] extract_floats_from_atoms(Atom[] atoms) {
        ArrayList<Float> floats = new ArrayList<Float>();
        for (Atom atom : atoms) {
            floats.add(atom.toFloat());
        }
        return floats.toArray(new Float[0]);
    }

    public Float get_maximum() {
        return this.maximum;
    }

    public Float get_minimum() {
        return this.minimum;
    }

    protected void initialize_extrema(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("minimum")) {
            this.set_minimum(this.extract_floats_from_atoms(argument_map
                .get("minimum"))[0]);
        } else {
            this.minimum = null;
        }
        if (argument_map.containsKey("maximum")) {
            this.set_maximum(argument_map.get("maximum")[0].toFloat());
        } else {
            this.maximum = null;
        }
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
        this.initialize_extrema(argument_map);
    }

    protected Float[] maximum_bound_floats(Float[] floats) {
        if (this.maximum == null) {
            return floats;
        }
        for (int i = 0, j = floats.length; i < j; i++) {
            if (this.maximum < floats[i]) {
                floats[i] = this.maximum;
            }
        }
        return floats;
    }

    protected Float[] minimum_bound_floats(Float[] floats) {
        if (this.minimum == null) {
            return floats;
        }
        for (int i = 0, j = floats.length; i < j; i++) {
            if (floats[i] < this.minimum) {
                floats[i] = this.minimum;
            }
        }
        return floats;
    }

    public void set_maximum(Float maximum) {
        this.maximum = maximum;
        this.sort_extrema();
    }

    public void set_minimum(Float minimum) {
        this.minimum = minimum;
        this.sort_extrema();
    }

    protected void sort_extrema() {
        if ((this.minimum != null) && (this.maximum != null)) {
            Float[] extrema = new Float[] {
                this.minimum, this.maximum
            };
            Arrays.sort(extrema);
            this.minimum = extrema[0];
            this.maximum = extrema[1];
        }
    }
}
