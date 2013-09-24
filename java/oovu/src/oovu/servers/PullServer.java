package oovu.servers;

import java.util.Map;

import com.cycling74.max.Atom;

public class PullServer extends AudioServer {

    public static PullServer allocate(Integer module_id, String desired_name,
        Atom[] argument_list) {
        return (PullServer) ModuleMemberServer.allocate_from_label("PullNode",
            module_id, desired_name, argument_list);
    }

    public PullServer(ModuleServer module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public PullServer new_instance(Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new PullServer(module_node, argument_map);
    }

}
