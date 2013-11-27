package oovu.datatypes;

import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.Setter;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

abstract public class BoundedArrayDatatype extends BoundedDatatype {
    protected int length;

    public BoundedArrayDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new MessageHandlerBuilder("length")
                .with_callback(new Setter() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        AttributeServer server =
                            (AttributeServer) built_message_handler.client;
                        server.reoutput_value();
                        return null;
                    }
                })
                .with_getter(new MessageHandlerCallback() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        return Atoms.to_atoms(built_message_handler.get_name(),
                            BoundedArrayDatatype.this.get_length());
                    }
                }).with_is_meta_relevant(true).with_is_state_relevant(true)
                .with_setter(new Setter() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        int new_length = BoundedArrayDatatype.this.length;
                        if (0 < arguments.length) {
                            new_length = arguments[0].toInt();
                        }
                        BoundedArrayDatatype.this.set_length(new_length);
                        return null;
                    }
                }).build(this.client));
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
