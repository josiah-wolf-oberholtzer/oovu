package oovu.datatypes;

import com.cycling74.max.*;
import java.util.*;
import oovu.nodes.*;

public class GenericDatatype extends Datatype {

    public GenericDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] { 0. });
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        return input;
    }

}
