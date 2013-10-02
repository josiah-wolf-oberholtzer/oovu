package oovu;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.clients.MaxPeer;
import oovu.clients.MessagePasserCallback;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public class Binding extends MaxPeer implements MessagePasser {

    private class RebindCallback implements Executable {

        public final Binding client;
        public final Atom[] arguments;

        public RebindCallback(Binding client, Atom[] arguments) {
            this.client = client;
            this.arguments = arguments;
        }

        @Override
        public void execute() {
            this.client.bind(this.arguments);
        }
    }

    protected OscAddressNode osc_address_node;

    public Binding(Atom[] arguments) {
        this.declareIO(3, 2);
        this.osc_address_node = null;
        try {
            MaxSystem.deferLow(new RebindCallback(this, arguments));
        } catch (UnsatisfiedLinkError e) {
            this.bind(arguments);
        }
    }

    @Override
    public void anything(String message, Atom[] arguments) {
        if (this.getInlet() == 2) {
            if (message.equals("bind")) {
                this.bind(arguments);
            }
        } else {
            OscAddress osc_address = null;
            if (this.getInlet() == 1) {
                osc_address = OscAddress.from_cache("./:" + message);
            } else if ((1 < message.length()) && (message.charAt(0) == ':')) {
                osc_address = OscAddress.from_cache("./" + message);
            } else {
                if (message.charAt(0) == '/') {
                    osc_address = OscAddress.from_cache("." + message);
                } else {
                    osc_address = OscAddress.from_cache(message);
                }
                if (osc_address.message_handler_name == null) {
                    osc_address = OscAddress.from_cache(osc_address.toString()
                        + "/:value");
                }
            }
            Request request = new Request(this, osc_address, arguments);
            this.handle_request(request);
        }
    }

    public void attach(OscAddressNode osc_address_node) {
        this.detach();
        if (osc_address_node != null) {
            this.osc_address_node = osc_address_node;
            this.osc_address_node.add_binding(this);
        }
    }

    public void bind(Atom[] arguments) {
        this.detach();
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
        if (found_osc_address_node == null) {
            MaxObject.error("Couldn't create address node: "
                + osc_address.toString());
            return;
        }
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
        if (this.osc_address_node == null) {
            return null;
        }
        return this.get_osc_address().toString();
    }

    @Override
    public void notifyDeleted() {
        this.detach();
    }
}
