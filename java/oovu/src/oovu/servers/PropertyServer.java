package oovu.servers;

import java.util.Map;

import oovu.environment.Response;
import oovu.environment.ValueResponse;

import com.cycling74.max.Atom;

public class PropertyServer extends AttributeServer {

    public static PropertyServer allocate(Integer module_id, String desired_name,
        Atom[] argument_list) {
        return (PropertyServer) ModuleMemberServer.allocate_from_label(
            "PropertyNode", module_id, desired_name, argument_list);
    }

    public PropertyServer(ModuleServer module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public PropertyServer new_instance(Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new PropertyServer(module_node, argument_map);
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
