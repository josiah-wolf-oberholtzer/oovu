package oovu.datatypes;

import java.util.Map;

import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class IntegerArrayDatatype extends BoundedArrayDatatype {

    public IntegerArrayDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public IntegerArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new int[] {
            0
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Atom[] result = this.ensure_length(input);
        Float[] floats = this.extract_bounded_floats_from_atoms(result);
        for (Float f : floats) {
            result[0] = Atom.newAtom(f.intValue());
        }
        return result;
    }
}
