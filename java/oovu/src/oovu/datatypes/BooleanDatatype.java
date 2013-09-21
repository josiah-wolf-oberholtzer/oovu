package oovu.datatypes;

import java.util.Map;
import com.cycling74.max.Atom;
import oovu.environment.InterfaceHandler;
import oovu.nodes.*;

public class BooleanDatatype extends GenericDatatype {

    private class ToggleInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "toggle";
        }

        @Override
        public Atom[][] run(Node node, Atom[] arguments) {
            AttributeNode attribute_node = (AttributeNode) node;
            Atom[] quality = attribute_node.get_value();
            boolean value = false;
            if (quality != null) {
                value = quality[0].toBoolean();
            }
            value = !value;
            attribute_node.set_value(new Atom[] { Atom.newAtom(value) });
            attribute_node.reoutput_value();
            return null;
        }
    }

    public BooleanDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_interface_handler(new ToggleInterfaceHandler());
        }
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new boolean[] { false });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Atom[] result = new Atom[1];
        if (0 < input.length) {
            if (input[0].isString()) {
                result[0] = Atom.newAtom(true);
            } else {
                result[0] = Atom.newAtom(input[0].toBoolean());
            }
        }
        return result;
    }

}
