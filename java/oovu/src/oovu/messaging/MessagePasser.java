package oovu.messaging;

import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;

public interface MessagePasser {

    public OscAddress get_osc_address();

    public OscAddressNode get_osc_address_node();

    public String get_osc_address_string();

    public void handle_request(Request request);

    public void handle_response(Response response);
}
