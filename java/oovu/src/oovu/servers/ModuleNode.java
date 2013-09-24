package oovu.servers;

import java.util.Map;

import oovu.Binding;
import oovu.environment.Environment;
import oovu.environment.InterfaceRequest;
import oovu.environment.Request;
import oovu.environment.Response;
import oovu.environment.ValueRequest;
import oovu.proxies.NodeProxy;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class ModuleNode extends Node {

    public static ModuleNode allocate(Integer module_id) {
        ModuleNode module_node = Environment.modules_by_module_id
            .get(module_id);
        if (module_node != null) {
            return module_node;
        }
        module_node = new ModuleNode(module_id, null);
        Environment.modules_by_module_id.put(module_id, module_node);
        return module_node;
    }

    public final Integer module_id;

    public ModuleNode(Integer module_id, Map<String, Atom[]> argument_map) {
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
    public Node get_parent_node() {
        if (Environment.root_node.child_nodes.containsKey(this.name)
            && (Environment.root_node.child_nodes.get(this.name) == this)) {
            return Environment.root_node;
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
        for (NodeProxy node_proxy : this.node_proxies) {
            node_proxy.handle_response(response);
        }
        for (Binding binding : this.bindings) {
            binding.handle_response(response);
        }
        Environment.root_node.handle_response(response);
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
        String acquired_name = Node.find_unique_name(desired_name,
            Environment.root_node.child_nodes.keySet());
        this.name = acquired_name;
        Environment.root_node.child_nodes.put(acquired_name, this);
        this.register_osc_address();
        for (Node member_node : this.child_nodes.values()) {
            member_node.register_osc_address();
        }
    }

    @Override
    public void unregister_name() {
        for (Node member_node : this.child_nodes.values()) {
            member_node.unregister_osc_address();
        }
        this.unregister_osc_address();
        Environment.root_node.child_nodes.remove(this.name);
        this.name = null;
    }

}
