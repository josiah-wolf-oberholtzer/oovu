package oovu.environment;

import com.cycling74.max.Atom;
import oovu.nodes.Node;


abstract public class Response {

    public final Node source;
    public final Atom[][] payload;
    public final Request original_request;
    
    public Response(Node source, Atom[][] payload, Request original_request) {
        this.source = source;
        this.payload = payload;
        this.original_request = original_request;
    }

    public String get_relative_osc_address(Node relative_node) {
        if (relative_node == null || this.source == null) {
            return null;
        } else if (relative_node == this.source) {
            return null;
        } else if (relative_node == this.source.get_parent_node()) {
            return "/" + this.source.get_name();
        } else if (relative_node == Environment.root_node) {
            return this.source.get_osc_address();
        }
        return null;
    }
}
