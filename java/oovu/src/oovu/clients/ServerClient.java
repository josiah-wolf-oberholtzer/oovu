package oovu.clients;

import oovu.addressing.OscAddressNode;
import oovu.messaging.InterfaceRequest;
import oovu.messaging.InterfaceResponse;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.messaging.ValueRequest;
import oovu.messaging.ValueResponse;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

abstract public class ServerClient extends MaxPeer {

    protected Server server;

    public void attach_to_server(Server server) {
        this.detach_from_server();
        if (server != null) {
            this.server = server;
            server.server_clients.add(this);
        }
    }

    public void detach_from_server() {
        if (this.server != null) {
            this.server.server_clients.remove(this);
        }
        this.server = null;
    }

    public Server get_server() {
        return this.server;
    }

    public String get_osc_address() {
        return this.server.get_osc_address();
    }
    
    public OscAddressNode get_osc_address_node() {
        return this.server.get_osc_address_node();
    }
    
    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        this.get_server().handle_request(request);
    }

    @Override
    public void notifyDeleted() {
        Server node = this.get_server();
        if (node == null) {
            return;
        }
        node.server_clients.remove(this);
        node.deallocate_if_necessary();
    }

}
