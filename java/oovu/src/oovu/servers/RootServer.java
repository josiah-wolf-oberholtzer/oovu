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
    public String get_osc_address() {
        return "/";
    }

    @Override
    public void handle_request(Request request) {
    }

}
