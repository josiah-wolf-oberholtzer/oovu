package oovu.clients;

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

abstract public class ServerClient extends MaxObject implements MessagePasser {

    protected Server server;

    @Override
    public void anything(String message, Atom[] arguments) {
        Request request = null;
        if (this.getInlet() == 1) {
            request = new InterfaceRequest(this, ".", message, arguments);
        } else if ((1 < message.length()) && (message.charAt(0) == ':')) {
            request = new InterfaceRequest(this, ".", message.substring(1),
                arguments);
        } else {
            Atom[] input = Atom.newAtom(message, arguments);
            request = new ValueRequest(this, ".", input);
        }
        this.handle_request(request);
    }

    protected Server get_node() {
        return this.server;
    }

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        this.get_node().handle_request(request);
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        String relative_osc_address = response.get_relative_osc_address(this
            .get_node());
        if (ValueResponse.class.isInstance(response)) {
            if (relative_osc_address != null) {
                for (Atom[] output : response.payload) {
                    this.output_value_response_payload(Atom.newAtom(
                        relative_osc_address, output));
                }
            } else {
                for (Atom[] output : response.payload) {
                    this.output_value_response_payload(output);
                }
            }
        } else if (InterfaceResponse.class.isInstance(response)) {
            if (relative_osc_address != null) {
                for (Atom[] output : response.payload) {
                    this.output_value_response_payload(Atom.newAtom(
                        relative_osc_address, output));
                }
            } else {
                for (Atom[] output : response.payload) {
                    this.output_interface_response_payload(output);
                }
            }
        }
    }

    @Override
    public void list(Atom[] input) {
        Request request = null;
        if (this.getInlet() == 0) {
            request = new ValueRequest(this, ".", input);
        }
        this.handle_request(request);
    }

    @Override
    public void notifyDeleted() {
        Server node = this.get_node();
        if (node == null) {
            return;
        }
        node.server_clients.remove(this);
        MaxObject.post("DELETING: " + this.toString() + " / " + node.toString()
            + "\n");
        node.deallocate_if_necessary();
    }

    public void output_interface_response_payload(Atom[] payload) {
        this.outlet(this.getInfoIdx(), payload);
    }

    public void output_value_response_payload(Atom[] payload) {
        this.outlet(1, payload);
        this.outlet(0, "set", payload);
    }

}
