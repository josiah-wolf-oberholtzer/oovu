package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.events.EventHandler;
import oovu.events.EventTypes;
import oovu.messaging.DatatypeMessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.AudioReceiveServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class AudioSendDatatype extends OscAddressDatatype {

    private class AudioReceiversChangedEventHandler extends EventHandler {

        @Override
        public EventTypes get_event() {
            return EventTypes.AUDIO_RECEIVERS_CHANGED;
        }

        @Override
        public void run() {
            Atom[] value = AudioSendDatatype.this.get_value();
            AudioSendDatatype.this.set_value(value);
            Server server = AudioSendDatatype.this.client;
            if (server != null) {
                server.handle_response(server.generate_dumpmeta_response());
            }
        }
    }

    private class GetDestinationIdMessageHandler extends DatatypeMessageHandler {

        public GetDestinationIdMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getdestinationid";
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("destinationid");
            result[0][1] =
                Atom.newAtom(AudioSendDatatype.this.get_destination_id());
            return result;
        }
    }

    private class GetDestinationsMessageHandler extends DatatypeMessageHandler {

        public GetDestinationsMessageHandler(AttributeServer attribute_server) {
            super(attribute_server);
        }

        @Override
        public String get_name() {
            return "getdestinations";
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] =
                Atom.newAtom("destinations",
                    Atom.newAtom(AudioSendDatatype.this.get_destinations()));
            return result;
        }
    }

    public AudioSendDatatype(Atom[] arguments) {
        this(null, Server.process_atom_arguments(arguments));
    }

    public AudioSendDatatype(AttributeServer client,
        Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            this.client
                .add_event_handler(new AudioReceiversChangedEventHandler());
            this.client.add_message_handler(new GetDestinationIdMessageHandler(
                client));
            this.client.add_message_handler(new GetDestinationsMessageHandler(
                client));
        }
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new String[] {
            "---"
        });
    }

    public Integer get_destination_id() {
        AudioReceiveServer destination_server = this.get_destination_server();
        if (destination_server == null) {
            return System.identityHashCode(this.client);
        }
        return System.identityHashCode(destination_server);
    }

    public AudioReceiveServer get_destination_server() {
        Atom[] value = this.get_value();
        String destination_name = value[0].getString();
        if (destination_name.equals("---")) {
            return null;
        }
        OscAddress osc_address = OscAddress.from_cache(destination_name);
        return AudioReceiveServer.audio_receive_servers.get(osc_address);
    }

    public String[] get_destinations() {
        String[] destination_names =
            new String[AudioReceiveServer.audio_receive_servers.size()];
        OscAddress[] osc_addresses =
            AudioReceiveServer.audio_receive_servers.keySet().toArray(
                new OscAddress[0]);
        for (int i = 0, j = osc_addresses.length; i < j; i++) {
            destination_names[i] = osc_addresses[i].toString();
        }
        Arrays.sort(destination_names);
        return destination_names;
    }

    @Override
    public Atom[] process_input(Atom[] input) {
        String destination_name = "---";
        if (0 < input.length) {
            destination_name = input[0].getString();
        }
        OscAddress osc_address = null;
        try {
            osc_address = OscAddress.from_cache(destination_name);
        } catch (RuntimeException e) {
        }
        if ((osc_address != null)
            && (AudioReceiveServer.audio_receive_servers
                .containsKey(osc_address))) {
            destination_name = osc_address.toString();
        } else {
            destination_name = "---";
        }
        return Atom.newAtom(new String[] {
            destination_name
        });
    }
}
