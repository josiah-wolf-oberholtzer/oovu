package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.events.Event;
import oovu.events.EventHandler;
import oovu.events.types.DspReceiversChangedEvent;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.servers.AttributeServer;
import oovu.servers.DspReceiveServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class AudioSendDatatype extends OscAddressDatatype {

    private class DspReceiversChangedEventHandler extends EventHandler {

        public DspReceiversChangedEventHandler(Server client) {
            super(client, DspReceiversChangedEvent.class);
        }

        @Override
        public void run(Event event) {
            AttributeServer client = (AttributeServer) this.client;
            Atom[] value = client.datatype.get_value();
            client.datatype.set_value(value);
            client.make_deferred_request(client, "dumpmeta", null);
        }
    }

    private class GetDestinationIdMessageHandler extends
        InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getdestinationid";
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

    private class GetDestinationsMessageHandler extends
        InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getdestinations";
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
            client
                .add_event_handler(new DspReceiversChangedEventHandler(client));
            Environment.event_service.subscribe(client,
                DspReceiversChangedEvent.class, null);
            this.client
                .add_message_handler(new GetDestinationIdMessageHandler());
            this.client
                .add_message_handler(new GetDestinationsMessageHandler());
        }
    }

    @Override
    public Atom[] get_default() {
        return Atom.newAtom(new String[] {
            "---"
        });
    }

    public Integer get_destination_id() {
        DspReceiveServer destination_server = this.get_destination_server();
        if (destination_server == null) {
            return System.identityHashCode(this.client);
        }
        return System.identityHashCode(destination_server);
    }

    public DspReceiveServer get_destination_server() {
        Atom[] value = this.get_value();
        String destination_name = value[0].getString();
        if (destination_name.equals("---")) {
            return null;
        }
        OscAddress osc_address = OscAddress.from_cache(destination_name);
        return DspReceiveServer.dsp_receive_servers.get(osc_address);
    }

    public String[] get_destinations() {
        String[] destination_names =
            new String[DspReceiveServer.dsp_receive_servers.size()];
        OscAddress[] osc_addresses =
            DspReceiveServer.dsp_receive_servers.keySet().toArray(
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
            && (DspReceiveServer.dsp_receive_servers.containsKey(osc_address))) {
            destination_name = osc_address.toString();
        } else {
            destination_name = "---";
        }
        return Atom.newAtom(new String[] {
            destination_name
        });
    }
}
