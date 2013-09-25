package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class InterfaceResponse extends Response {

    public InterfaceResponse(Server source, Atom[][] response_list,
        Request original_request) {
        super(source, response_list, original_request);
    }
}
