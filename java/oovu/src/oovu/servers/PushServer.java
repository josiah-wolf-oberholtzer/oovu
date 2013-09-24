package oovu.servers;

import java.util.Map;

import com.cycling74.max.Atom;

public class PushServer extends AudioServer {

    public static PushServer allocate(Integer module_id, String desired_name,
        Atom[] argument_list) {
        return (PushServer) ModuleMemberServer.allocate_from_label("PushNode",
            module_id, desired_name, argument_list);
    }

    public PushServer(ModuleServer module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public PushServer new_instance(Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new PushServer(module_node, argument_map);
    }

}
