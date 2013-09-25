package oovu.messaging;

import com.cycling74.max.Atom;

public class ValueRequest extends Request {

    public ValueRequest(MessagePasser source, String destination_address,
        Atom[] payload) {
        super(source, destination_address, payload);
    }
}
