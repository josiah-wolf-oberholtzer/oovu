package oovu.messaging;

import oovu.addressing.OscAddress;

import com.cycling74.max.Atom;

public class Request {

    public final MessagePasser source;
    public final OscAddress destination;
    public final Atom[] payload;

    public Request(MessagePasser source, OscAddress destination, Atom[] payload) {
        this.source = source;
        this.destination = destination;
        this.payload = payload;
    }
}
