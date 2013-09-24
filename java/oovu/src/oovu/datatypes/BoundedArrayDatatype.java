package oovu.datatypes;

import java.util.Map;

import oovu.environment.InterfaceHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class BoundedArrayDatatype extends BoundedDatatype {

    private class GetLengthInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getlength";
        }

        @Override
        public Atom[][] run(Server node, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom.newAtom("length", Atom
                .newAtom(new int[] { BoundedArrayDatatype.this.get_length() }));
            return result;
        }
    }

    private class SetLengthInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "length";
        }

        @Override
        public Atom[][] run(Server node, Atom[] arguments) {
            if (0 < arguments.length) {
                if (arguments[0].isFloat() || arguments[0].isInt()) {
                    Integer length = arguments[0].getInt();
                    if (0 < length) {
                        BoundedArrayDatatype.this.set_length(length);
                    }
                }
                AttributeServer attribute = (AttributeServer) node;
                attribute.reoutput_value();
            }
            return null;
        }
    }

    protected Integer length = 1;

    public BoundedArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        this.initialize_length(argument_map);
        if (this.client != null) {
            client.add_interface_handler(new GetLengthInterfaceHandler());
            client.add_interface_handler(new SetLengthInterfaceHandler());
        }
    }

    protected Integer get_length() {
        return this.length;
    }

    protected void initialize_length(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("length")) {
            this.set_length(argument_map.get("length")[0].toInt());
        }
    }

    protected void set_length(Integer length) {
        if (length < 1) {
            length = 1;
        }
        this.length = length;
    }

}
