package oovu.servers;

import java.util.Map;

import oovu.events.Event;
import oovu.events.EventTypes;

import com.cycling74.max.Atom;

public class AudioReceiveServer extends AudioServer {

    public static AudioReceiveServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (AudioReceiveServer) ModuleMemberServer.allocate_from_label(
            "AudioReceive", module_id, desired_name, argument_list);
    }

    public AudioReceiveServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
        Event.notify_observers(EventTypes.AUDIO_RECEIVERS_CHANGED);
    }

    @Override
    public AudioReceiveServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new AudioReceiveServer(module_node, argument_map);
    }
}
