package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.addressing.Environment;
import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.Server;
import oovu.servers.members.AudioServer;

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

    private class GetPushIdMessageHandler extends DatatypeMessageHandler {

        public GetPushIdMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getpushid";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String osc_address_string = PushDatatype.this.value[0].getString();
            int id = System.identityHashCode(this.attribute_server);
            AudioServer audio_server = Environment.push_addresses
                .get(osc_address_string);
            if (audio_server != null) {
                id = System.identityHashCode(audio_server);
            }
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("pushid");
            result[0][1] = Atom.newAtom(id);
            return result;
        }
    }

    public PushDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public PushDatatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client.add_message_handler(new GetPushAddressesMessageHandler(
                this.client));
            this.client.add_message_handler(new GetPushIdMessageHandler(
                this.client));
        }
        this.initialize_default_value(argument_map);
    }
}
