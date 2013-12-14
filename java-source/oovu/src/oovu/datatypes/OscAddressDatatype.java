package oovu.datatypes;

import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class OscAddressDatatype extends StringDatatype {
    public OscAddressDatatype(Atom[] arguments) {
        this(null, MaxIO.to_map(arguments));
    }

    public OscAddressDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new String[] {
            "---"
        });
    }
}
