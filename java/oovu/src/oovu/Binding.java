package oovu;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.clients.MaxPeer;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;

import com.cycling74.max.Atom;

public class Binding extends MaxPeer implements MessagePasser {

    protected OscAddressNode osc_address_node = null;

    public Binding(Atom[] arguments) {
        if (arguments.length == 0) {
            return;
        }
        String osc_address_string = arguments[0].getString();
        OscAddress osc_address = OscAddress.from_cache(osc_address_string);
        if (osc_address.has_wildcard_tokens) {
            return;
        }
        OscAddressNode osc_address_node = Environment.root_osc_address_node
            .create_address(osc_address, false);
        this.attach(osc_address_node);
    }

    public void attach(OscAddressNode osc_address_node) {
        this.detach();
        if (osc_address_node != null) {
            this.osc_address_node = osc_address_node;
            this.osc_address_node.add_binding(this);
        }
    }

    public void detach() {
        if (this.osc_address_node != null) {
            this.osc_address_node.remove_binding(this);
            this.osc_address_node.prune_if_necessary();
        }
        this.osc_address_node = null;
    }

    @Override
    public String get_osc_address() {
        if (this.osc_address_node == null) {
            return null;
        }
        return this.osc_address_node.get_osc_address();
    }

    @Override
    public OscAddressNode get_osc_address_node() {
        return this.osc_address_node;
    }

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        // this.get_osc_address_node().handle_request(request);
    }

    @Override
    public void notifyDeleted() {
        this.detach();
    }
}
