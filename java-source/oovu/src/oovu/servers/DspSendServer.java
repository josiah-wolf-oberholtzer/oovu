package oovu.servers;

import java.util.Map;

import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSendServer extends ModuleMemberServer {

    public static DspSendServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (DspSendServer) ModuleMemberServer.allocate_from_label(
            "DspSendServer", module_id, desired_name, argument_list);
    }

    public DspSendServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public DspSendServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
    }

    @Override
    public State get_state() {
        return null;
    }
}
