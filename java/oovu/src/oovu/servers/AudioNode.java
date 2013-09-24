package oovu.servers;

import java.util.Map;

import oovu.environment.Request;
import oovu.environment.Response;

import com.cycling74.max.Atom;

abstract public class AudioNode extends ModuleMemberNode {

    public AudioNode(ModuleNode module_node, Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public void handle_request(Request request) {
    }

    @Override
    public void handle_response(Response response) {
    }

}
