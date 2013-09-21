package oovu.datatypes;

import java.util.Map;
import com.cycling74.max.Atom;
import oovu.environment.InterfaceHandler;
import oovu.nodes.*;

public class OptionDatatype extends StringDatatype {

    private class GetOptionsInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getoptions";
        }

        @Override
        public Atom[][] run(Node node, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom.newAtom(OptionDatatype.this.get_options());
            result[0] = Atom.newAtom("options", result[0]);
            return result;
        }
    }

    private class SetOptionsInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "options";
        }

        @Override
        public Atom[][] run(Node node, Atom[] arguments) {
            String[] options = OptionDatatype.this.extract_strings_from_atoms(arguments);
            OptionDatatype.this.set_options(options);
            return null;
        }
    }
    
    protected String[] options = null;

    public OptionDatatype(AttributeNode client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_interface_handler(new GetOptionsInterfaceHandler());
            this.client.add_interface_handler(new SetOptionsInterfaceHandler());
        }
        this.initialize_options(argument_map);
    }
    
    protected void initialize_options(Map<String, Atom[]> argument_map) {
        if (argument_map.containsKey("options")) {
            this.set_options(this.extract_strings_from_atoms(argument_map.get("options")));
        }
    }
    
    protected String[] get_options() {
        return this.options;
    }
    
    protected void set_options(String[] options) {
        this.options = options;
    }

}
