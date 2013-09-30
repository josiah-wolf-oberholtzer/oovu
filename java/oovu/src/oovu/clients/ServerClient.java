package oovu.clients;

import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.servers.Server;

import com.cycling74.max.MaxSystem;

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
            this.server.deallocate_if_necessary();
        }
        this.server = null;
    }

    protected void generate_server_client_creation_callback() {
        try {
            ServerClientCreationCallback callback = new ServerClientCreationCallback(
                this);
            MaxSystem.deferLow(callback);
        } catch (UnsatisfiedLinkError e) {
        }
    }

    @Override
    public OscAddress get_osc_address() {
        return this.server.get_osc_address();
    }

    @Override
    public OscAddressNode get_osc_address_node() {
        return this.server.get_osc_address_node();
    }

    @Override
    public String get_osc_address_string() {
        return this.get_osc_address().toString();
    }

    public Server get_server() {
        return this.server;
    }

    @Override
    public void notifyDeleted() {
        this.detach_from_server();
    }
}
