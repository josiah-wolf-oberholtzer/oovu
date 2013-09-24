package oovu.servers;

import java.util.Map;

import com.cycling74.max.Atom;

public class MethodNode extends AttributeNode {

    public static MethodNode allocate(Integer module_id, String desired_name,
        Atom[] argument_list) {
        return (MethodNode) ModuleMemberNode.allocate_from_label("MethodNode",
            module_id, desired_name, argument_list);
    }

    public MethodNode(ModuleNode module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public MethodNode new_instance(Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleNode module_node = ModuleNode.allocate(module_id);
        return new MethodNode(module_node, argument_map);
    }

    @Override
    public void reoutput_value() {

    }

}
