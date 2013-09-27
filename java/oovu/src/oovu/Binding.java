package oovu;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.clients.AddressNodeClient;

import com.cycling74.max.Atom;

public class Binding extends AddressNodeClient {

    protected OscAddressNode target_osc_address_node = null;

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

    public void attach(OscAddressNode target_osc_address_node) {
        this.detach();
        if (target_osc_address_node != null) {
            this.target_osc_address_node = target_osc_address_node;
            this.target_osc_address_node.add_binding(this);
        }
    }

    public void detach() {
        if (this.target_osc_address_node != null) {
            this.target_osc_address_node.remove_binding(this);
        }
        this.target_osc_address_node = null;
    }
}
