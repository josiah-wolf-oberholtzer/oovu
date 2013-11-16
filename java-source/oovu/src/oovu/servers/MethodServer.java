package oovu.servers;

import java.util.Map;

import com.cycling74.max.Atom;

public class MethodServer extends AttributeServer {

    public static MethodServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (MethodServer) ModuleMemberServer.allocate_from_label(
            "MethodServer", module_id, desired_name, argument_list);
    }

    public MethodServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public MethodServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public MethodServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new MethodServer(module_node, argument_map);
    }

    @Override
    public void reoutput_value() {
    }
}
