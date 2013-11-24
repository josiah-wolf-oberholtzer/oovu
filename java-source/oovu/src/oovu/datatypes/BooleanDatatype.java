package oovu.datatypes;

import java.util.Map;

import oovu.messaging.ActionMessageHandler;
import oovu.messaging.Atoms;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class BooleanDatatype extends GenericDatatype {

    private class ToggleMessageHandler extends ActionMessageHandler {

        public ToggleMessageHandler(Server client) {
            super(client, "toggle");
        }

        @Override
        public void call_after() {
            BooleanDatatype.this.client.reoutput_value();
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            BooleanDatatype.this.toggle();
            return null;
        }
    }

    public BooleanDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public BooleanDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new ToggleMessageHandler(
                this.client));
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
