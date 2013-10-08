package oovu.servers;

import java.util.Map;

import oovu.states.State;

import com.cycling74.max.Atom;

abstract public class AudioServer extends ModuleMemberServer {

    public AudioServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public State get_state() {
        return null;
    }
}
