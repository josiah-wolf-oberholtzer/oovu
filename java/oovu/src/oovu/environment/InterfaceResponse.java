package oovu.environment;

import oovu.nodes.Node;

import com.cycling74.max.Atom;


public class InterfaceResponse extends Response {

    public InterfaceResponse(Node source, Atom[][] response_list,
        Request original_request) {
        super(source, response_list, original_request);
    }

}
