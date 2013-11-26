package oovu.messaging;

import oovu.addresses.OscAddressNode;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class Response {
    public final Server source;
    public final Atom[][] payload;
    public final Request original_request;

    public Response(Server source, Atom[][] payload, Request original_request) {
        this.source = source;
        this.payload = payload;
        this.original_request = original_request;
    }

    public String get_relative_osc_address(
        OscAddressNode relative_osc_address_node) {
        return this.source.get_osc_address_node()
            .get_relative_osc_address_string(relative_osc_address_node);
    }

    @Override
    public String toString() {
        String payload_info = null;
        if (this.payload != null) {
            payload_info = "";
            for (Atom[] element : this.payload) {
                payload_info += "[" + Atom.toOneString(element) + "]";
            }
        }
        return "Response ["
            + (this.source != null ? "source=" + this.source + ", " : "")
            + (this.payload != null ? "payload=" + payload_info + ", " : "[]")
            + (this.original_request != null ? "original_request="
                + this.original_request : "") + "]";
    }
}
