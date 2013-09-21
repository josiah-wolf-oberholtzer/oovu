package oovu.datatypes;

import java.util.Map;
import com.cycling74.max.Atom;
import oovu.nodes.*;

public class IntegerDatatype extends BoundedDatatype {

    public IntegerDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new int[] { 0 });
    }

}
