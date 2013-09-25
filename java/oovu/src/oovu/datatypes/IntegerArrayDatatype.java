package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class IntegerArrayDatatype extends BoundedArrayDatatype {

    public IntegerArrayDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new int[] {
            0
        });
    }
}
