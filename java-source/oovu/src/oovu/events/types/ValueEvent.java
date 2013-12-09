package oovu.events.types;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class ValueEvent extends ServerEvent {
    public final Atom[] payload;

    public ValueEvent(Server publisher, Atom[] payload) {
        super(publisher);
        this.payload = payload;
    }
}
