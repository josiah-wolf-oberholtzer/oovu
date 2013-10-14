package oovu.datatypes;

import java.util.Map;

import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class FilesystemPathDatatype extends StringDatatype {

    public FilesystemPathDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public FilesystemPathDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }
}
