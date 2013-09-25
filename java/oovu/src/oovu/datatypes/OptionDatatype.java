package oovu.datatypes;

import java.util.Map;

import oovu.messaging.DatatypeMessageHandler;
import oovu.messaging.MessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

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
        public String get_name() {
            return "options";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String[] options = OptionDatatype.this.extract_strings_from_atoms(arguments);
            OptionDatatype.this.set_options(options);
            return null;
        }
        
        public void call_after() {
        	this.attribute_server.reoutput_value();
        }
    }
    
    protected String[] options = null;

    public OptionDatatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new GetOptionsMessageHandler(this.client));
            this.client.add_message_handler(new SetOptionsMessageHandler(this.client));
        }
        this.initialize_options(argument_map);
    }
    
    protected String[] get_options() {
        return this.options;
    }
    
    protected void initialize_options(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("options")) {
            this.set_options(this.extract_strings_from_atoms(argument_map.get("options")));
        }
    }
    
    protected void set_options(String[] options) {
        this.options = options;
    }

}
