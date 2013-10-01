package oovu.datatypes;

import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.AttributeServer;

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
                    Integer new_length = arguments[0].getInt();
                    BoundedArrayDatatype.this.set_length(new_length);
                }
            }
            return null;
        }
    }

    protected int length;

    public BoundedArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            client
                .add_message_handler(new GetLengthMessageHandler(this.client));
            client
                .add_message_handler(new SetLengthMessageHandler(this.client));
        }
    }

    protected Atom[] ensure_length(Atom[] input) {
        if (this.length == input.length) {
            return input;
        }
        Atom[] output = new Atom[this.length];
        if (this.length < input.length) {
            for (int i = 0; i < this.length; i++) {
                output[i] = input[i];
            }
        } else {
            int i = 0;
            for (int j = input.length; i < j; i++) {
                output[i] = input[i];
            }
            for (int j = output.length; i < j; i++) {
                output[i] = Atom.newAtom(0.);
            }
        }
        return output;
    }

    public Integer get_length() {
        return this.length;
    }

    protected void initialize_length(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("length")) {
            int new_length = argument_map.get("length")[0].toInt();
            this.set_length(new_length);
        } else {
            this.set_length(1);
        }
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
        this.initialize_extrema(argument_map);
        this.initialize_length(argument_map);
    }

    public void set_length(int new_length) {
        if (new_length < 1) {
            new_length = 1;
        }
        this.length = new_length;
    }
}
