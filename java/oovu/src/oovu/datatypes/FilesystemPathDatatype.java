package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeNode;

import com.cycling74.max.Atom;

public class FilesystemPathDatatype extends StringDatatype {

    public FilesystemPathDatatype(AttributeNode client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }

}
