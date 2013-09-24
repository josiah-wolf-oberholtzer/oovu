package oovu.environment;

import oovu.messaging.Request;
import oovu.messaging.Response;


public interface Dispatcher {

    public void handle_request(Request request);
    
    public void handle_response(Response response);
    
}
