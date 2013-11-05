package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class GenericDatatype extends Datatype {

    public GenericDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public GenericDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Integer get_arity() {
        return null;
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] {
            0.
        });
    }

    @Override
    protected void initialize_prerequisites(Map<String, Atom[]> argument_map) {
    }

    @Override
    public boolean is_binding_relevant() {
        return false;
    }

    @Override
    public boolean is_rampable() {
        return false;
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        return input;
    }
}
