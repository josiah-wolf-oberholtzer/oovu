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
        ModuleServer module_node = Environment.modules_by_module_id
            .get(module_id);
        if (module_node != null) {
            return module_node;
        }
        module_node = new ModuleServer(module_id, null);
        Environment.modules_by_module_id.put(module_id, module_node);
        return module_node;
    }

    public final Integer module_id;

    public ModuleServer(Integer module_id, Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_id = module_id;
    }

    @Override
    public void deallocate() {
        Environment.modules_by_module_id.remove(this.module_id);
        this.unregister_name();
    }

    @Override
    public String get_osc_address() {
        if (this.name == null) {
            return null;
        }
        return '/' + this.name;
    }

    @Override
    public Server get_parent_node() {
        if (Environment.root_server.child_nodes.containsKey(this.name)
            && (Environment.root_server.child_nodes.get(this.name) == this)) {
            return Environment.root_server;
        }
        return null;
    }

    @Override
    public int get_reference_count() {
        return this.node_proxies.size() + this.child_nodes.size();
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
        for (ServerClient node_proxy : this.node_proxies) {
            node_proxy.handle_response(response);
        }
        Environment.root_server.handle_response(response);
    }

    @Override
    public void register_name(String desired_name) {
        if (desired_name == this.name) {
            return;
        }
        this.unregister_name();
        if (desired_name == null) {
            return;
        }
        String acquired_name = OscAddressNode.find_unique_name(desired_name,
            Environment.root_server.child_nodes.keySet());
        this.name = acquired_name;
        Environment.root_server.child_nodes.put(acquired_name, this);
        this.register_at_osc_address();
        for (Server member_node : this.child_nodes.values()) {
            member_node.register_at_osc_address();
        }
    }

    @Override
    public void unregister_name() {
        for (Server member_node : this.child_nodes.values()) {
            member_node.unregister_from_osc_address();
        }
        this.unregister_from_osc_address();
        Environment.root_server.child_nodes.remove(this.name);
        this.name = null;
    }

}
