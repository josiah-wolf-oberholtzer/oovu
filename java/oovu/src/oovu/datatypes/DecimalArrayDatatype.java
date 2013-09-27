package oovu.datatypes;

import java.util.Map;

import oovu.servers.members.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class DecimalArrayDatatype extends BoundedArrayDatatype {

    public DecimalArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        this.initialize_default_value(argument_map);
    }

    public DecimalArrayDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0.
        });
    }
}
