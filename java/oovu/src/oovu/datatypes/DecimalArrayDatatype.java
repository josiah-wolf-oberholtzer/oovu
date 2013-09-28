package oovu.datatypes;

import java.util.Map;

import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class DecimalArrayDatatype extends BoundedArrayDatatype {

    public DecimalArrayDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public DecimalArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0.
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Atom[] result = this.ensure_length(input);
        double[] doubles = this.extract_bounded_doubles_from_atoms(result);
        for (int i = 0, j = result.length; i < j; i++) {
            result[i] = Atom.newAtom(doubles[i]);
        }
        return result;
    }
}
