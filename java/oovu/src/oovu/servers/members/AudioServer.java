package oovu.servers.members;

import java.util.Map;

import oovu.servers.ModuleMemberServer;
import oovu.servers.ModuleServer;

import com.cycling74.max.Atom;

abstract public class AudioServer extends ModuleMemberServer {

    public AudioServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }
}
