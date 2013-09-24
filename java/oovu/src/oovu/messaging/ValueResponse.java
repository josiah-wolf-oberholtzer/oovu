package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;


public class ValueResponse extends Response {

    public ValueResponse(Server source, Atom[][] response_list,
        Request original_request) {
        super(source, response_list, original_request);
    }

}
