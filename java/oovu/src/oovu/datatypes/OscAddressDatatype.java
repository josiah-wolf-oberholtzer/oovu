package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeNode;

import com.cycling74.max.Atom;

public class OscAddressDatatype extends StringDatatype {

    public OscAddressDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

}
