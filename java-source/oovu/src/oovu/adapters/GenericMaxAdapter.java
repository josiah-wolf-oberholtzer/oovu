package oovu.adapters;

import oovu.clients.MaxPeer;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

public class GenericMaxAdapter extends MaxAdapter {
    public GenericMaxAdapter(MaxPeer max_peer) {
        super(max_peer);
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        if (this.max_peer.get_osc_address_node() == null) {
            return;
        }
        String relative_osc_address =
            response.get_relative_osc_address(this.max_peer
                .get_osc_address_node());
        for (Atom[] output : response.payload) {

            if (output[0].equals(MaxAdapter.value_atom)) {
                output = Atom.removeFirst(output);
                if (relative_osc_address != null) {
                    output = Atom.newAtom(relative_osc_address, output);
                }
                this.outlet(1, output);
                this.outlet(0, Atom.newAtom("set", output));
            } else {
                if (relative_osc_address != null) {
                    String message = output[0].getString();
                    output = Atom.removeFirst(output);
                    output =
                        Atom.newAtom(relative_osc_address + "/:" + message,
                            output);
                    this.outlet(1, output);
                    this.outlet(0, Atom.newAtom("set", output));
                    // this.output_value_response_payload(output);
                } else {
                    this.outlet(2, output);
                }
            }
        }
    }
}
