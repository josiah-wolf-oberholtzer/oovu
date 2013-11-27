package oovu.datatypes;

import java.util.Map;

import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class BooleanDatatype extends GenericDatatype {
    public BooleanDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public BooleanDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new MessageHandlerBuilder("toggle")
                .with_arity(0)
                .with_callback(new MessageHandlerCallback() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        AttributeServer server =
                            (AttributeServer) built_message_handler.client;
                        server.reoutput_value();
                        return null;
                    }
                }).with_is_binding_relevant(true)
                .with_setter(new MessageHandlerCallback() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        BooleanDatatype.this.toggle();
                        return null;
                    }
                }).build(this.client));
        }
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new boolean[] {
            false
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Atom[] result = new Atom[1];
        if (0 < input.length) {
            result[0] = Atom.newAtom(input[0].toBoolean());
        }
        return result;
    }

    public void toggle() {
        boolean value = this.get_value()[0].toBoolean();
        this.set_value(Atom.newAtom(new boolean[] {
            !value
        }));
    }
}
