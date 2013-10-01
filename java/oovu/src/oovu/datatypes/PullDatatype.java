package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.addressing.Environment;
import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class PullDatatype extends OscAddressDatatype {

    private class GetPullAddressesMessageHandler extends DatatypeMessageHandler {

        public GetPullAddressesMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getpulladdresses";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String[] pull_addresses = Environment.pull_addresses.keySet()
                .toArray(new String[0]);
            Arrays.sort(pull_addresses);
            result[0] = Atom.newAtom("addresses", Atom.newAtom(pull_addresses));
            return result;
        }
    }

    public PullDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public PullDatatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (client != null) {
            client.add_message_handler(new GetPullAddressesMessageHandler(
                this.client));
        }
        this.initialize_default_value(argument_map);
    }
}
