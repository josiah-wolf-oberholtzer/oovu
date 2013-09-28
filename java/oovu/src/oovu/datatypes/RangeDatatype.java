package oovu.datatypes;

import java.util.Map;

import oovu.servers.Server;
import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class RangeDatatype extends BoundedDatatype {

    public RangeDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public RangeDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0., 1.
        });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        return new Atom[0];
    }
}
