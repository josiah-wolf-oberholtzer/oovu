package oovu.messaging;

public interface MessagePasser {
    public void handle_request(Request request);

    public void handle_response(Response response);
}
