package oovu.events;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class ValueEvent extends ServerEvent {
    public final Atom[] payload;

    public ValueEvent(Server publisher, Atom[] payload) {
        super(publisher);
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ValueEvent ["
            + (this.payload != null ? "payload="
                + Atom.toOneString(this.payload) + ", " : "")
            + (this.publisher != null ? "publisher=" + this.publisher : "")
            + "]";
    }
}