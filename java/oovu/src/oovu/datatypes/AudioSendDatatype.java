package oovu.datatypes;

import java.util.Map;

import oovu.events.EventHandler;
import oovu.events.EventTypes;
import oovu.servers.AttributeServer;
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
        }
    }
}
