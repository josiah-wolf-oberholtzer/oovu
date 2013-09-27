package oovu.messaging;

import oovu.addressing.OscAddressNode;
import oovu.servers.Server;

import org.apache.commons.lang3.ArrayUtils;

import com.cycling74.max.Atom;

abstract public class Response {

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
        OscAddressNode[] source_parentage = this.source.get_osc_address_node()
            .get_parentage();
        OscAddressNode[] relative_parentage = relative_osc_address_node
            .get_parentage();
        ArrayUtils.reverse(source_parentage);
        ArrayUtils.reverse(relative_parentage);
        int counter = 0;
        while ((counter < source_parentage.length)
            && (counter < relative_parentage.length)
            && (source_parentage[counter] == relative_parentage[counter])) {
            counter += 1;
        }
        StringBuilder string_builder = new StringBuilder();
        while (counter < source_parentage.length) {
            string_builder.append("/");
            string_builder.append(source_parentage[counter].get_name());
            counter += 1;
        }
        return string_builder.toString();
    }
}
