package oovu.servers;

import java.util.Map;

import oovu.messaging.Request;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

abstract public class AudioServer extends ModuleMemberServer {

    public AudioServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    public void handle_request(Request request) {
    }

    @Override
    public void handle_response(Response response) {
    }
}
