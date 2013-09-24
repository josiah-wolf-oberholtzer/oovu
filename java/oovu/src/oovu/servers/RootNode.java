package oovu.servers;

import oovu.Binding;
import oovu.environment.Environment;
import oovu.environment.OscAddressNode;
import oovu.environment.Request;
import oovu.environment.Response;
import oovu.proxies.NodeProxy;

public class RootNode extends Node {

    public RootNode() {
        super(null);
        this.name = "OOVU";
    }

    @Override
    protected void deallocate() {
    }

    @Override
    public String get_osc_address() {
        return "/";
    }

    @Override
    public OscAddressNode get_osc_address_node() {
    	return Environment.root_osc_address_node;
    }
    
    @Override
    public Node get_parent_node() {
        return null;
    }

    @Override
    public int get_reference_count() {
        return this.node_proxies.size() + this.child_nodes.size();
    }

    @Override
    public void handle_request(Request request) {
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        for (NodeProxy node_proxy : this.node_proxies) {
            node_proxy.handle_response(response);
        }
        for (Binding binding : this.bindings) {
            binding.handle_response(response);
        }
    }

    @Override
    public void register_name(String desired_name) {
    }

    @Override
    public void unregister_name() {
    }

}
