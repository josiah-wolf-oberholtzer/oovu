package oovu.servers;

import oovu.clients.ServerClient;
import oovu.environment.Environment;
import oovu.messaging.Request;
import oovu.messaging.Response;

public class RootServer extends Server {

    public RootServer() {
        super(null);
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
    }

    @Override
    protected void deallocate() {
        for (Server child_server : this.child_servers) {
            child_server.detach_from_parent_server();
        }
        for (ServerClient server_client : this.server_clients) {
            server_client.detach_from_server();
        }
    }

    @Override
    public String get_osc_address() {
        return "/";
    }

    @Override
    public int get_reference_count() {
        return this.server_clients.size() + this.child_servers.size();
    }

    @Override
    public void handle_request(Request request) {
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        for (ServerClient server_client : this.server_clients) {
            server_client.handle_response(response);
        }
    }
}
