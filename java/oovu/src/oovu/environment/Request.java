package oovu.environment;

import com.cycling74.max.Atom;


abstract public class Request {

    public final Dispatcher source;
    public final String destination_address;
    public final Atom[] payload;
    
    public Request(Dispatcher source, String destination_address, Atom[] payload) {
        this.source = source;
        this.destination_address = destination_address;
        this.payload = payload;
    }
    
}
