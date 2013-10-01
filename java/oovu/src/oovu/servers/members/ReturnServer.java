package oovu.servers.members;

import java.util.Map;

import oovu.messaging.Response;
import oovu.servers.AttributeServer;
import oovu.servers.ModuleMemberServer;
import oovu.servers.ModuleServer;

import com.cycling74.max.Atom;

public class ReturnServer extends AttributeServer {

    public static ReturnServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (ReturnServer) ModuleMemberServer.allocate_from_label(
            "ReturnNode", module_id, desired_name, argument_list);
    }

    public ReturnServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public ReturnServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new ReturnServer(module_node, argument_map);
    }

    @Override
    public void reoutput_value() {
        Atom[] value = this.get_value();
        this.set_value(value);
        Atom[][] payload = new Atom[1][];
        payload[0] = this.get_value();
        Response response = new Response(this, payload, null);
        this.handle_response(response);
    }
}
