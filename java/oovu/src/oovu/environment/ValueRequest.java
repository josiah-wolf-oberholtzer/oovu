package oovu.environment;

import com.cycling74.max.Atom;


public class ValueRequest extends Request {

    public ValueRequest(Dispatcher source, String destination_address, Atom[] payload) {
        super(source, destination_address, payload);
    }

}
