package oovu.datatypes;

import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class FilesystemPathDatatype extends StringDatatype {
    public FilesystemPathDatatype(Atom[] arguments) {
        this(null, MaxIO.to_map(arguments));
    }

    public FilesystemPathDatatype(
        AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
    }
}
