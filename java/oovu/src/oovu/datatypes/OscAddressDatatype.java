package oovu.datatypes;

import java.util.Map;

import oovu.servers.members.AttributeServer;

import com.cycling74.max.Atom;

public class OscAddressDatatype extends StringDatatype {

    public OscAddressDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }
}
