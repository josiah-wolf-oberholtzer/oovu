package oovu.clients;

import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

abstract public class ServerClient extends AddressedMaxPeer {
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
        } else if (!arguments[0].isInt() && (arguments[0].getString().charAt(0) != '#')) {
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

    @Override
    public OscAddress get_osc_address() {
        if (this.server != null) {
            return this.server.get_osc_address();
        }
        return null;
    }

    @Override
    public OscAddressNode get_osc_address_node() {
        if (this.server != null) {
            return this.server.get_osc_address_node();
        }
        return null;
    }

    @Override
    public String get_osc_address_string() {
        OscAddress osc_address = this.get_osc_address();
        if (osc_address != null) {
            return osc_address.toString();
        }
        return null;
    }

    public Server get_server() {
        return this.server;
    }

    @Override
    public void notifyDeleted() {
        this.detach_from_server();
    }
}
