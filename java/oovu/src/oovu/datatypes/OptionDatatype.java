package oovu.datatypes;

import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class OptionDatatype extends StringDatatype {

    private class GetOptionsMessageHandler extends DatatypeMessageHandler {

        public GetOptionsMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getoptions";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom.newAtom(OptionDatatype.this.get_options());
            result[0] = Atom.newAtom("options", result[0]);
            return result;
        }
    }

    private class SetOptionsMessageHandler extends DatatypeMessageHandler {

        public SetOptionsMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public void call_after() {
            this.attribute_server.reoutput_value();
        }

        @Override
        public String get_name() {
            return "options";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String[] options = OptionDatatype.this
                .extract_strings_from_atoms(arguments);
            OptionDatatype.this.set_options(options);
            return null;
        }
    }

    protected String[] options;

    public OptionDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public OptionDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new GetOptionsMessageHandler(
                this.client));
            this.client.add_message_handler(new SetOptionsMessageHandler(
                this.client));
        }
    }

    protected String[] get_options() {
        return this.options;
    }

    protected void initialize_options(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("options")) {
            this.set_options(this.extract_strings_from_atoms(argument_map
                .get("options")));
        }
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
        this.initialize_options(argument_map);
    }

    protected void set_options(String[] options) {
        this.options = options;
    }
}
