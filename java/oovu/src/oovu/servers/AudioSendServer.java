package oovu.servers;

import java.util.Arrays;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.events.EventHandler;
import oovu.events.EventTypes;
import oovu.messaging.MessageHandler;

import com.cycling74.max.Atom;

public class AudioSendServer extends AudioServer {

    private class AudioReceiversChangedEventHandler extends EventHandler {

        @Override
        public EventTypes get_event() {
            return EventTypes.AUDIO_RECEIVERS_CHANGED;
        }

        @Override
        public void run() {
            String destination = AudioSendServer.this.get_destination();
            AudioSendServer.this.set_destination(destination);
            AudioSendServer.this.handle_response(AudioSendServer.this
                .generate_dumpmeta_response());
        }
    }

    private class GetDestinationIdMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getdestination";
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
            Atom[][] result = new Atom[0][2];
            result[0][0] = Atom.newAtom("destinationid");
            result[0][1] =
                Atom.newAtom(AudioSendServer.this.get_destination_id());
            return result;
        }
    }

    private class GetDestinationMessageHandler extends MessageHandler {

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
            Atom[][] result = new Atom[0][2];
            result[0][0] = Atom.newAtom("destination");
            result[0][1] = Atom.newAtom(AudioSendServer.this.get_destination());
            return result;
        }
    }

    private class GetDestinationsMessageHandler extends MessageHandler {

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
            Atom[][] result = new Atom[0][];
            String[] destination_names =
                AudioSendServer.this.get_destinations();
            Atom[] destination_atoms = Atom.newAtom(destination_names);
            result[0] = Atom.newAtom("destinationid", destination_atoms);
            return result;
        }
    }

    private class SetDestinationMessageHandler extends MessageHandler {

        @Override
        public void call_after() {
            AudioSendServer.this.handle_response(AudioSendServer.this
                .generate_dumpmeta_response());
        }

        @Override
        public String get_name() {
            return "destination";
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String destination_name = "---";
            if (0 < arguments.length) {
                destination_name = arguments[0].getString();
            }
            AudioSendServer.this.set_destination(destination_name);
            return null;
        }
    }

    public static AudioSendServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (AudioSendServer) ModuleMemberServer.allocate_from_label(
            "AudioSend", module_id, desired_name, argument_list);
    }

    protected AudioReceiveServer destination_server = null;

    public AudioSendServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
        this.add_event_handler(new AudioReceiversChangedEventHandler());
        this.add_message_handler(new GetDestinationMessageHandler());
        this.add_message_handler(new GetDestinationsMessageHandler());
        this.add_message_handler(new GetDestinationIdMessageHandler());
        this.add_message_handler(new SetDestinationMessageHandler());
    }

    public String get_destination() {
        if (this.destination_server == null) {
            return "---";
        }
        return this.destination_server.get_osc_address_string();
    }

    public Integer get_destination_id() {
        if (this.destination_server == null) {
            return System.identityHashCode(this);
        }
        return System.identityHashCode(this.destination_server);
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
    public AudioSendServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new AudioSendServer(module_node, argument_map);
    }

    public void set_destination(String destination_name) {
        OscAddress osc_address = OscAddress.from_cache(destination_name);
        AudioReceiveServer destination_server =
            AudioReceiveServer.audio_receive_servers.get(osc_address);
        if (destination_server != null) {
            this.destination_server = destination_server;
        } else {
            this.destination_server = null;
        }
    }
}
