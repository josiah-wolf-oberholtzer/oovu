package oovu.events.types;

import com.cycling74.max.Atom;

import oovu.servers.Server;

public class ValueEvent extends ServerEvent {

    public final Atom[] payload;
    
    public ValueEvent(Server publisher, Atom[] payload) {
        super(publisher);
        this.payload = payload;
    }

}
