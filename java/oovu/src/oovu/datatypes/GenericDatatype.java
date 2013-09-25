package oovu.datatypes;

import java.util.Map;

import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class GenericDatatype extends Datatype {

    public GenericDatatype(AttributeServer client,
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
        return input;
    }
}
