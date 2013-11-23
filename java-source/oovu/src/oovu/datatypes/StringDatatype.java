package oovu.datatypes;

import java.util.ArrayList;
import java.util.Map;

import oovu.messaging.Atoms;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class StringDatatype extends GenericDatatype {

    public StringDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public StringDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    protected String[] extract_strings_from_atoms(Atom[] atoms) {
        ArrayList<String> strings = new ArrayList<String>();
        for (Atom atom : atoms) {
            strings.add(atom.toString());
        }
        return strings.toArray(new String[0]);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new String[] {
            ""
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        return Atom.newAtom(new String[] {
            Atom.toOneString(input)
        });
    }
}
