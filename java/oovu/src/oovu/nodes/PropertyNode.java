package oovu.nodes;

import java.util.Map;

import oovu.environment.Response;
import oovu.environment.ValueResponse;

import com.cycling74.max.Atom;

public class PropertyNode extends AttributeNode {

    public static PropertyNode allocate(Integer module_id, String desired_name,
        Atom[] argument_list) {
        return (PropertyNode) ModuleMemberNode.allocate_from_label(
            "PropertyNode", module_id, desired_name, argument_list);
    }

    public PropertyNode(ModuleNode module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public PropertyNode new_instance(Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleNode module_node = ModuleNode.allocate(module_id);
        return new PropertyNode(module_node, argument_map);
    }

    @Override
    public void reoutput_value() {
        Atom[] value = this.get_value();
        this.set_value(value);
        Atom[][] payload = new Atom[1][];
        payload[0] = this.get_value();
        Response response = new ValueResponse(this, payload, null);
        this.handle_response(response);
    }
}
