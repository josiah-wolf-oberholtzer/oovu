package oovu.datatypes;

import java.util.Map;

import oovu.messaging.Atoms;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class OscAddressDatatype extends StringDatatype {
    public OscAddressDatatype(Atom[] arguments) {
        this(null, Atoms.to_map(arguments));
    }

    public OscAddressDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }
}
