package oovu.datatypes;

import java.util.Map;

import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class IntegerDatatype extends BoundedDatatype {

    public IntegerDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public IntegerDatatype(AttributeServer client,
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
        double[] doubles = this.extract_bounded_doubles_from_atoms(input);
        Atom[] result = new Atom[1];
        if (0 < input.length) {
            result[0] = Atom.newAtom(new Double(doubles[0]).intValue());
        }
        return result;
    }
}
