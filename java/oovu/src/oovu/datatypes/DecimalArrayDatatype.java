package oovu.datatypes;

import java.util.Map;
import com.cycling74.max.Atom;
import oovu.nodes.*;

public class DecimalArrayDatatype extends BoundedArrayDatatype {

    public DecimalArrayDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] { 0. });
    }

}
