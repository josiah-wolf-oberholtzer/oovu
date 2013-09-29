package oovu.clients;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

abstract public class MaxPeer extends MaxObject implements MessagePasser {

    @Override
    public void anything(String message, Atom[] arguments) {
        String osc_address_string = null;
        if (this.getInlet() == 1) {
            osc_address_string = "./:" + message;
        } else if ((1 < message.length()) && (message.charAt(0) == ':')) {
            osc_address_string = "./" + message;
        } else {
            osc_address_string = "./:value";
            arguments = Atom.newAtom(message, arguments);
        }
        Request request = new Request(this,
            OscAddress.from_cache(osc_address_string), arguments);
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
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        Atom value_atom = Atom.newAtom("value");
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
                this.output_interface_response_payload(output);
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
        this.outlet(this.getInfoIdx(), payload);
    }

    public void output_value_response_payload(Atom[] payload) {
        this.outlet(1, payload);
        this.outlet(0, "set", payload);
    }
}