package oovu.environment;

import oovu.servers.Node;

import com.cycling74.max.Atom;


public class ValueResponse extends Response {

    public ValueResponse(Node source, Atom[][] response_list,
        Request original_request) {
        super(source, response_list, original_request);
    }

}
