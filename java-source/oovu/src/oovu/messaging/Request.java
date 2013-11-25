package oovu.messaging;

import oovu.addresses.OscAddress;

import com.cycling74.max.Atom;

public class Request {
    public final MessagePasser source;
    public final OscAddress destination;
    public final Atom[] payload;
    public final boolean call_after;

    public Request(MessagePasser source, OscAddress destination,
        Atom[] payload, boolean call_after) {
        this.source = source;
        this.destination = destination;
        this.payload = payload;
        this.call_after = call_after;
    }

    @Override
    public String toString() {
        StringBuilder string_builder = new StringBuilder();
        string_builder.append(this.destination.toString());
        string_builder.append(" ");
        string_builder.append(Atom.toOneString(this.payload));
        return string_builder.toString();
    }
}
