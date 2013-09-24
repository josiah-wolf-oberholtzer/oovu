package oovu.datatypes;

import java.util.Map;

import oovu.environment.InterfaceHandler;
import oovu.servers.AttributeNode;
import oovu.servers.Node;

import com.cycling74.max.Atom;

public class BooleanDatatype extends GenericDatatype {

    private class ToggleInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "toggle";
        }

        @Override
        public Atom[][] run(Node node, Atom[] arguments) {
            AttributeNode attribute_node = (AttributeNode) node;
            BooleanDatatype.this.toggle();
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
    
    public void toggle()  {
    	boolean value = this.get_value()[0].toBoolean();
    	this.set_value(Atom.newAtom(new boolean[]{! value}));
    }

}
