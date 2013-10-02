package oovu.clients;

import java.util.Set;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.maxadapters.MaxAdapter;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.servers.Server;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

abstract public class MaxPeer extends MaxObject implements MessagePasser {

    protected MaxAdapter max_adapter;

    @Override
    public void anything(String message, Atom[] arguments) {
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
        Set<OscAddressNode> osc_address_nodes = this.get_osc_address_node()
            .search(request.destination);
        for (OscAddressNode osc_address_node : osc_address_nodes) {
            Server server = osc_address_node.get_server();
            if (server != null) {
                server.handle_request(request);
            }
        }
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        Atom value_atom = Atom.newAtom("value");
        if (this.get_osc_address_node() == null) {
            return;
        }
        String relative_osc_address = response.get_relative_osc_address(this
            .get_osc_address_node());
        for (Atom[] output : response.payload) {
            if (output[0].equals(value_atom)) {
                output = Atom.removeFirst(output);
                if (relative_osc_address != null) {
                    output = Atom.newAtom(relative_osc_address, output);
                }
                this.output_value_response_payload(output);
            } else {
                if (relative_osc_address != null) {
                    String message = output[0].getString();
                    output = Atom.removeFirst(output);
                    output = Atom.newAtom(
                        relative_osc_address + "/:" + message, output);
                    this.output_value_response_payload(output);
                } else {
                    this.output_interface_response_payload(output);
                }
            }
        }
    }

    @Override
    public void list(Atom[] input) {
        Request request = null;
        if (this.getInlet() == 0) {
            request = new Request(this, OscAddress.from_cache("./:value"),
                input);
        }
        this.handle_request(request);
    }

    public void output_interface_response_payload(Atom[] payload) {
        try {
            this.outlet(this.getInfoIdx(), payload);
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public void output_value_response_payload(Atom[] payload) {
        try {
            this.outlet(1, payload);
            this.outlet(0, "set", payload);
        } catch (UnsatisfiedLinkError e) {
            // Environment.log(e);
        }
    }
}