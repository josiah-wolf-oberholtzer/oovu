package oovu;

import oovu.adapters.GenericMaxAdapter;
import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.clients.MaxPeer;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.servers.ModuleMemberServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public class Proxy extends MaxPeer implements MessagePasser {
    private class RebindCallback implements Executable {
        public final Proxy client;
        public final Atom[] arguments;

        public RebindCallback(Proxy client, Atom[] arguments) {
            this.client = client;
            this.arguments = arguments;
        }

        @Override
        public void execute() {
            this.client.bind(this.arguments);
        }
    }

    protected OscAddressNode osc_address_node;
    protected String message_handler_name;

    public Proxy(Atom[] arguments) {
        this.declareIO(3, 2);
        this.max_adapter = new GenericMaxAdapter(this);
        this.osc_address_node = null;
        this.message_handler_name = null;
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
            if (this.message_handler_name == null) {
                if (this.getInlet() == 1) {
                    osc_address = OscAddress.from_cache("./:" + message);
                } else if ((1 < message.length()) && (message.charAt(0) == ':')) {
                    osc_address = OscAddress.from_cache("./" + message);
                } else {
                    Server server = this.get_osc_address_node().get_server();
                    if (server instanceof ModuleMemberServer) {
                        osc_address = OscAddress.from_cache("./:value");
                        arguments = Atom.newAtom(message, arguments);
                    } else {
                        if (message.charAt(0) == '/') {
                            osc_address = OscAddress.from_cache("." + message);
                        } else {
                            osc_address = OscAddress.from_cache(message);
                        }
                        if (osc_address.message_handler_name == null) {
                            osc_address =
                                OscAddress.from_cache(osc_address.toString()
                                    + "/:value");
                        }
                    }
                }
            } else {
                arguments = Atom.newAtom(message, arguments);
                osc_address =
                    OscAddress.from_cache("./:" + this.message_handler_name);
            }
            Request request = new Request(this, osc_address, arguments, true);
            this.handle_request(request);
        }
    }

    public void attach(OscAddressNode osc_address_node) {
        this.detach();
        if (osc_address_node != null) {
            this.osc_address_node = osc_address_node;
            this.osc_address_node.add_proxy(this);
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
            OscAddressNode numbered_child =
                osc_address_node.get_numbered_child(module_id);
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
        OscAddressNode found_osc_address_node =
            osc_address_node.create_address(osc_address, false);
        if (found_osc_address_node == null) {
            MaxObject.error("Couldn't create address node: "
                + osc_address.toString());
            return;
        }
        this.attach(found_osc_address_node);
        if (osc_address.message_handler_name != null) {
            this.message_handler_name = osc_address.message_handler_name;
        } else {
            this.message_handler_name = null;
        }
        if (this.osc_address_node.get_server() != null) {
            this.osc_address_node.get_server().make_deferred_request(this,
                "dumpmeta", null);
        }
    }

    public void detach() {
        if (this.osc_address_node != null) {
            this.osc_address_node.remove_proxy(this);
            this.osc_address_node.prune_if_necessary();
        }
        this.osc_address_node = null;
        this.message_handler_name = null;
    }

    public String get_message_handler_name() {
        return this.message_handler_name;
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
    public void handle_response(Response response) {
        if (this.message_handler_name == null) {
            this.max_adapter.handle_response(response);
        } else {
            for (Atom[] atoms : response.payload) {
                if (!atoms[0].getString().equals(this.message_handler_name)) {
                    continue;
                }
                Atom[][] payload = new Atom[1][];
                payload[0] = Atom.newAtom("value", Atom.removeFirst(atoms));
                Response new_response =
                    new Response(response.source, payload,
                        response.original_request);
                this.max_adapter.handle_response(new_response);
            }
        }
    }

    @Override
    public void list(Atom[] input) {
        if (this.message_handler_name == null) {
            this.anything(":value", input);
        } else {
            OscAddress osc_address =
                OscAddress.from_cache("./:" + this.message_handler_name);
            Request request = new Request(this, osc_address, input, true);
            this.handle_request(request);
        }
    }

    @Override
    public void notifyDeleted() {
        this.detach();
    }
}
