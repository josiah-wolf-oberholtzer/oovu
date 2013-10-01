package oovu;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.clients.MaxPeer;
import oovu.clients.MessagePasserCallback;
import oovu.messaging.MessagePasser;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

public class Binding extends MaxPeer implements MessagePasser {

    protected OscAddressNode osc_address_node;

    public Binding(Atom[] arguments) {
        this.declareIO(2, 2);
        OscAddress osc_address = null;
        if (arguments.length == 0) {
            return;
        }
        OscAddressNode osc_address_node = Environment.root_osc_address_node;
        if (arguments[0].isString()
            && (arguments[0].getString().charAt(0) == '#')) {
            return;
        } else if (arguments[0].isInt()) {
            int module_id = arguments[0].getInt();
            OscAddressNode numbered_child = osc_address_node
                .get_numbered_child(module_id);
            if (numbered_child == null) {
                numbered_child = new OscAddressNode(module_id);
                osc_address_node.add_child(numbered_child);
            }
            osc_address_node = numbered_child;
            if (1 < arguments.length) {
                osc_address = OscAddress.from_cache(arguments[1].getString());
            } else {
                osc_address = OscAddress.from_cache(".");
            }
        } else {
            osc_address = OscAddress.from_cache(arguments[0].getString());
        }
        if (osc_address.has_wildcard_tokens) {
            return;
        }
        OscAddressNode found_osc_address_node = osc_address_node
            .create_address(osc_address, false);
        this.attach(found_osc_address_node);
        if (this.osc_address_node.get_server() != null) {
            try {
                Server server = this.osc_address_node.get_server();
                MessagePasserCallback callback = new MessagePasserCallback(
                    this, server.generate_dumpmeta_response());
                MaxSystem.deferLow(callback);
            } catch (UnsatisfiedLinkError e) {
                // Environment.log(e);
            }
        }
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
    public OscAddress get_osc_address() {
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
    public String get_osc_address_string() {
        return this.get_osc_address().toString();
    }

    @Override
    public void notifyDeleted() {
        this.detach();
    }
}
