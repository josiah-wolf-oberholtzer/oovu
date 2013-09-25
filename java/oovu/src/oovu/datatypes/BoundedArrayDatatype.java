package oovu.datatypes;

import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

abstract public class BoundedArrayDatatype extends BoundedDatatype {

    private class GetLengthMessageHandler extends DatatypeMessageHandler {

        public GetLengthMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getlength";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom.newAtom("length", Atom.newAtom(new int[] {
                BoundedArrayDatatype.this.get_length()
            }));
            return result;
        }
    }

    private class SetLengthMessageHandler extends DatatypeMessageHandler {

        public SetLengthMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public void call_after() {
            this.attribute_server.reoutput_value();
        }

        @Override
        public String get_name() {
            return "length";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                if (arguments[0].isFloat() || arguments[0].isInt()) {
                    Integer length = arguments[0].getInt();
                    if (0 < length) {
                        BoundedArrayDatatype.this.set_length(length);
                    }
                }
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
            client
                .add_message_handler(new GetLengthMessageHandler(this.client));
            client
                .add_message_handler(new SetLengthMessageHandler(this.client));
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
