package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class FilesystemPathDatatype extends StringDatatype {

    public FilesystemPathDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

}
