package oovu.datatypes;

import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.GetterMessageHandler;
import oovu.messaging.SetterMessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class BoundedArrayDatatype extends BoundedDatatype {

    private class GetLengthMessageHandler extends GetterMessageHandler {

        public GetLengthMessageHandler(Server client) {
            super(client, "getlength");
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
            return Atoms.to_atoms("length",
                BoundedArrayDatatype.this.get_length());
        }
    }

    private class SetLengthMessageHandler extends SetterMessageHandler {

        public SetLengthMessageHandler(Server client) {
            super(client, "length");
        }

        @Override
        public void call_after() {
            BoundedArrayDatatype.this.client.reoutput_value();
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            int new_length = BoundedArrayDatatype.this.length;
            if (0 < arguments.length) {
                new_length = arguments[0].toInt();
            }
            BoundedArrayDatatype.this.set_length(new_length);
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

    @Override
    public Integer get_arity() {
        return this.length;
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
        if (new_length == this.length) {
            return;
        }
        this.length = new_length;
        if (this.value != null) {
            double[] doubles = Atom.toDouble(this.ensure_length(this.value));
            doubles = this.bound_doubles(doubles);
            if (this.multi_envelope != null) {
                this.multi_envelope.resize(new_length);
                doubles = this.multi_envelope.control_all_envelopes(doubles);
            }
            this.value = Atom.newAtom(doubles);
        }
    }
}
