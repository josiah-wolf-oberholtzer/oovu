package oovu.messaging;

import oovu.environment.Dispatcher;

import com.cycling74.max.Atom;


public class InterfaceRequest extends Request {

    public final String message_handler_name;
    
    public InterfaceRequest(Dispatcher dispatcher, String destination_address, 
        String message_handler_name, Atom[] payload) {
        super(dispatcher, destination_address, payload);
        this.message_handler_name = message_handler_name;
    }

}
