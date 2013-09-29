package oovu.messaging;

import oovu.addressing.OscAddressNode;

public interface MessagePasser {

    public String get_osc_address();

    public OscAddressNode get_osc_address_node();

    public void handle_request(Request request);

    public void handle_response(Response response);
}
