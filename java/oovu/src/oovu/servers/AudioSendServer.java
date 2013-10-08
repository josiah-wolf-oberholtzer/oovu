package oovu.servers;

import java.util.Map;

import oovu.events.EventHandler;
import oovu.events.EventTypes;

import com.cycling74.max.Atom;

public class AudioSendServer extends AudioServer {

    private class AudioReceiversChangedEventHandler extends EventHandler {

        @Override
        public EventTypes get_event() {
            return EventTypes.AUDIO_RECEIVERS_CHANGED;
        }

        @Override
        public void run() {
        }
    }

    public static AudioSendServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (AudioSendServer) ModuleMemberServer.allocate_from_label(
            "AudioSend", module_id, desired_name, argument_list);
    }

    protected AudioReceiveServer destination_server;

    public AudioSendServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
        this.add_event_handler(new AudioReceiversChangedEventHandler());
    }

    public String get_destination() {
        return this.destination_server.get_osc_address_string();
    }

    public String[] get_destinations() {
        return new String[0];
    }

    @Override
    public AudioSendServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new AudioSendServer(module_node, argument_map);
    }

    public void set_destination(String destination) {
    }
}
