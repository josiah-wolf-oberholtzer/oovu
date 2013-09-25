package oovu.servers;

import java.util.Map;

import oovu.clients.ServerClient;
import oovu.environment.Environment;
import oovu.environment.OscAddressNode;
import oovu.messaging.InterfaceRequest;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.messaging.ValueRequest;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class ModuleServer extends Server {

    public static ModuleServer allocate(Integer module_id) {
        OscAddressNode osc_address_node = Environment.root_osc_address_node
            .get_numbered_child(module_id);
        if (osc_address_node != null) {
            Server server = osc_address_node.get_server();
            if (server != null) {
                if (ModuleServer.class.isInstance(server)) {
                    return (ModuleServer) server;
                }
            }
            throw new RuntimeException("Bad module setup");
        } else {
            osc_address_node = new OscAddressNode("", module_id);
            Environment.root_osc_address_node.add_child(osc_address_node);
            ModuleServer module_server = new ModuleServer(module_id, null);
            module_server.attach_to_osc_address_node(osc_address_node);
            return module_server;
        }
    }

    public final Integer module_id;

    public ModuleServer(Integer module_id, Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.attach_to_parent_server(Environment.root_server);
        this.module_id = module_id;
    }

    @Override
    public int get_reference_count() {
        return this.server_clients.size() + this.child_servers.size();
    }

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        Response response = null;
        if (ValueRequest.class.isInstance(request)) {
            MaxObject.post(request.toString() + "\n");
        } else if (InterfaceRequest.class.isInstance(request)) {
            MaxObject.post(request.toString() + "\n");
        }
        this.handle_response(response);
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        for (ServerClient server_client : this.server_clients) {
            server_client.handle_response(response);
        }
        Environment.root_server.handle_response(response);
    }

}
