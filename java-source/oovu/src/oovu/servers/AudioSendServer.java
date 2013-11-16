package oovu.servers;

import java.util.Map;

import com.cycling74.max.Atom;

import oovu.states.State;


public class AudioSendServer extends ModuleMemberServer {

    public static AudioSendServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (AudioSendServer) ModuleMemberServer.allocate_from_label(
            "AudioSendServer", module_id, desired_name, argument_list);
    }

    public AudioSendServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public AudioSendServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
    }

    @Override
    public AudioSendServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new AudioSendServer(module_node, argument_map);
    }

    @Override
    public State get_state() {
        return null;
    }
    
}
