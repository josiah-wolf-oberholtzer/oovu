package oovu.messaging;

import java.util.Arrays;

import oovu.addresses.OscAddress;

import com.cycling74.max.Atom;

public class Request {
    public final MessagePasser source;
    public final OscAddress destination;
    public final Atom[] payload;
    public final boolean call_after;

    public Request(
        MessagePasser source,
        OscAddress destination,
        Atom[] payload,
        boolean call_after) {
        this.source = source;
        this.destination = destination;
        this.payload = payload;
        this.call_after = call_after;
    }

    @Override
    public String toString() {
        return "Request ["
            + (this.source != null ? "source=" + this.source + ", " : "")
            + (this.destination != null ? "destination=" + this.destination + ", " : "")
            + (this.payload != null ? "payload=" + Arrays.toString(this.payload) + ", "
                : "") + "call_after=" + this.call_after + "]";
    }
}
