package oovu;

import oovu.addressing.OscAddressNode;
import oovu.clients.AddressNodeClient;

public class Binding extends AddressNodeClient {

    protected OscAddressNode target_osc_address_node = null;

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
