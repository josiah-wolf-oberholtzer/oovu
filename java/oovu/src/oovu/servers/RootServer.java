package oovu.servers;

import oovu.addressing.Environment;
import oovu.clients.ServerClient;
import oovu.messaging.Request;

public class RootServer extends Server {

    public RootServer() {
        super(null);
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
    }
    
    public void clear() {
        for (Server child_server : this.child_servers) {
            child_server.detach_from_parent_server();
        }
        this.detach_from_parent_server();
        for (ServerClient server_client : this.server_clients) {
            server_client.detach_from_server();
        }
    }

    @Override
    public String get_osc_address() {
        return "/";
    }

    @Override
    public void handle_request(Request request) {
    }
}
