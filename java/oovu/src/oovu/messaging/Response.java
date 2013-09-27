package oovu.messaging;

import oovu.addressing.Environment;
import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class Response {

    public final Server source;
    public final Atom[][] payload;
    public final Request original_request;

    public Response(Server source, Atom[][] payload, Request original_request) {
        this.source = source;
        this.payload = payload;
        this.original_request = original_request;
    }

    public String get_relative_osc_address(Server relative_node) {
        if ((relative_node == null) || (this.source == null)) {
            return null;
        } else if (relative_node == this.source) {
            return null;
        } else if (relative_node == this.source.get_parent_server()) {
            return "/" + this.source.get_name();
        } else if (relative_node == Environment.root_server) {
            return this.source.get_osc_address();
        }
        return null;
    }
}
