package oovu.environment;


public interface Dispatcher {

    public void handle_request(Request request);
    
    public void handle_response(Response response);
    
}
