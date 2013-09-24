package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.environment.Environment;
import oovu.environment.InterfaceHandler;
import oovu.servers.AttributeNode;
import oovu.servers.Node;

import com.cycling74.max.Atom;

public class PushDatatype extends OscAddressDatatype {

    private class GetPushAddressesInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getpushaddresses";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String[] push_addresses = Environment.push_addresses.keySet()
                .toArray(new String[0]);
            Arrays.sort(push_addresses);
            result[0] = Atom.newAtom("addresses", Atom.newAtom(push_addresses));
            return result;
        }
    }

    public PushDatatype(AttributeNode client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client
                .add_interface_handler(new GetPushAddressesInterfaceHandler());
        }
    }

}
