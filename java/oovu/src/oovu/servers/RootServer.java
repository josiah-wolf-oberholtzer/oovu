package oovu.servers;

import oovu.Binding;
import oovu.clients.ServerClient;
import oovu.environment.Environment;
import oovu.environment.OscAddressNode;
import oovu.messaging.Request;
import oovu.messaging.Response;

public class RootServer extends Server {

    public RootServer() {
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
    public Server get_parent_node() {
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
        for (ServerClient node_proxy : this.node_proxies) {
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
