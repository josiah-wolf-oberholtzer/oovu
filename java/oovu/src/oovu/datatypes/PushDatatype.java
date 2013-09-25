package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.environment.Environment;
import oovu.messaging.DatatypeMessageHandler;
import oovu.messaging.MessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class PushDatatype extends OscAddressDatatype {

    private class GetPushAddressesMessageHandler extends DatatypeMessageHandler {

    	public GetPushAddressesMessageHandler(AttributeServer attribute_server) {
    		super(attribute_server);
    	}

        @Override
        public String get_name() {
            return "getpushaddresses";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String[] push_addresses = Environment.push_addresses.keySet()
                .toArray(new String[0]);
            Arrays.sort(push_addresses);
            result[0] = Atom.newAtom("addresses", Atom.newAtom(push_addresses));
            return result;
        }
    }

    public PushDatatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client
                .add_message_handler(new GetPushAddressesMessageHandler(this.client));
        }
    }

}
