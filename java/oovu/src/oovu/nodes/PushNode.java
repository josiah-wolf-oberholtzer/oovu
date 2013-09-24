package oovu.nodes;

import java.util.Map;

import com.cycling74.max.Atom;

public class PushNode extends AudioNode {

    public static PushNode allocate(Integer module_id, String desired_name,
        Atom[] argument_list) {
        return (PushNode) ModuleMemberNode.allocate_from_label("PushNode",
            module_id, desired_name, argument_list);
    }

    public PushNode(ModuleNode module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public PushNode new_instance(Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleNode module_node = ModuleNode.allocate(module_id);
        return new PushNode(module_node, argument_map);
    }

}
