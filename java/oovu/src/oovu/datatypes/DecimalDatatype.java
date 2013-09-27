package oovu.datatypes;

import java.util.Map;

import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class DecimalDatatype extends BoundedDatatype {

    public DecimalDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public DecimalDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        this.initialize_default_value(argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0.
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        Float[] floats = this.extract_bounded_floats_from_atoms(input);
        Atom[] result = new Atom[1];
        if (0 < input.length) {
            result[0] = Atom.newAtom(floats[0].floatValue());
        }
        return result;
    }
}
