package oovu.datatypes;

import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class BooleanDatatype extends GenericDatatype {
    public BooleanDatatype(Atom[] arguments) {
        this(null, MaxIO.to_map(arguments));
    }

    public BooleanDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.configure_toggle_message_handler();
        }
    }

    private void configure_toggle_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("toggle");
        builder.with_arity(0);
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                AttributeServer server =
                    (AttributeServer) built_message_handler.client;
                server.reoutput_value();
                return null;
            }
        });
        builder.with_is_binding_relevant(true);
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                BooleanDatatype.this.toggle();
                return null;
            }
        });
        this.client.add_message_handler(builder.build(this.client));
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
