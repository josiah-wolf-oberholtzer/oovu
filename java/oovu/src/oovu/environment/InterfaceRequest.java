package oovu.environment;

import com.cycling74.max.Atom;


public class InterfaceRequest extends Request {

    public final String interface_handler_name;
    
    public InterfaceRequest(Dispatcher dispatcher, String destination_address, 
        String interface_handler_name, Atom[] payload) {
        super(dispatcher, destination_address, payload);
        this.interface_handler_name = interface_handler_name;
    }

}
