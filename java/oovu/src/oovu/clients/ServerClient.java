package oovu.clients;

import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
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

    public void check_arguments(Atom[] arguments) {
        if (arguments.length < 2) {
            MaxObject.bail("OOVU server clients require a server ID and name.");
        } else if (!arguments[0].isInt()
            && (arguments[0].getString().charAt(0) != '#')) {
            MaxObject.bail("Invalid OOVU server ID.");
        }
    }

    public void detach_from_server() {
        if (this.server != null) {
            this.server.server_clients.remove(this);
            this.server.deallocate_if_necessary();
        }
        this.server = null;
    }

    protected void generate_message_passer_callback() {
        try {
            MessagePasserCallback callback = new MessagePasserCallback(this,
                this.get_server().generate_dumpmeta_response());
            MaxSystem.deferLow(callback);
        } catch (UnsatisfiedLinkError e) {
            // Environment.log(e);
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
