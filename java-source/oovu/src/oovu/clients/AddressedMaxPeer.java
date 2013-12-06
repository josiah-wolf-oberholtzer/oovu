package oovu.clients;

import java.util.Set;

import oovu.addresses.Addressed;
import oovu.addresses.Environment;
import oovu.addresses.OscAddressNode;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.servers.Server;

import com.cycling74.max.MaxObject;

abstract public class AddressedMaxPeer extends MaxPeer implements Addressed,
    MessagePasser {
    @Override
    public void dblclick() {
        for (String piece : Environment.root_osc_address_node
            .get_debug_pieces()) {
            MaxObject.post(piece);
        }
    }

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        } else if (this.get_osc_address_node() == null) {
            return;
        }
        Set<OscAddressNode> osc_address_nodes =
            this.get_osc_address_node().search(request.destination);
        for (OscAddressNode osc_address_node : osc_address_nodes) {
            Server server = osc_address_node.get_server();
            if (server != null) {
                server.handle_request(request);
            }
        }
    }
}