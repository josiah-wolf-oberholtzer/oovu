package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeNode;

import com.cycling74.max.Atom;

public class DecimalDatatype extends BoundedDatatype {

    public DecimalDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new double[] { 0. });
    }

}
