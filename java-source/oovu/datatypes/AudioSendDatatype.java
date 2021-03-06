package oovu.datatypes;

import java.util.Arrays;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.events.DspReceiversChangedEvent;
import oovu.events.Event;
import oovu.events.Subscription;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.servers.AttributeServer;
import oovu.servers.DspReceiveServer;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class AudioSendDatatype extends OscAddressDatatype {
    private class DspReceiversChangedSubscription extends Subscription {
        public DspReceiversChangedSubscription(Server client) {
            super(client, DspReceiversChangedEvent.class, null);
        }

        @Override
        public void handle_event(Event event) {
            AttributeServer client = (AttributeServer) this.subscriber;
            Atom[] value = client.datatype.get_value();
            client.datatype.set_value(value);
            client.make_deferred_request(client, "dumpmeta", null);
        }
    }

    public static String[] get_destinations() {
        String[] destination_names =
            new String[DspReceiveServer.dsp_receive_servers.size()];
        OscAddress[] osc_addresses =
            DspReceiveServer.dsp_receive_servers.keySet().toArray(new OscAddress[0]);
        for (int i = 0, j = osc_addresses.length; i < j; i++) {
            destination_names[i] = osc_addresses[i].toString();
        }
        Arrays.sort(destination_names);
        return destination_names;
    }

    public AudioSendDatatype(Atom[] arguments) {
        this(null, MaxIO.from_serialized_dict(arguments));
    }

    public AudioSendDatatype(AttributeServer client, Map<String, Atom[]> argument_map) {
        super(client, argument_map);
        if (this.client != null) {
            Subscription subscription = new DspReceiversChangedSubscription(client);
            subscription.subscribe();
            this.client.add_message_handler(new MessageHandlerBuilder("destinations")
                .with_getter(new MessageHandlerCallback() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        return MaxIO.to_atoms(built_message_handler.get_name(),
                            AudioSendDatatype.get_destinations());
                    }
                }).with_is_meta_relevant(true).build(this.client));
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
