package oovu.servers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.clients.ServerClient;
import oovu.states.GlobalState;
import oovu.states.State;

public class RootServer extends Server {

    public RootServer() {
        super(null);
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
    }

    @Override
    public void clear() {
        for (Server child_server : this.child_servers.toArray(new Server[0])) {
            child_server.detach_from_parent_server();
        }
        this.detach_from_parent_server();
        for (ServerClient server_client : this.server_clients) {
            server_client.detach_from_server();
        }
    }

    public List<ModuleServer> get_child_module_servers() {
        ArrayList<ModuleServer> module_servers = new ArrayList<ModuleServer>();
        for (Server child : this.child_servers) {
            if (child instanceof ModuleServer) {
                module_servers.add((ModuleServer) child);
            }
        }
        Collections.sort(module_servers);
        return module_servers;
    }

    @Override
    public OscAddress get_osc_address() {
        return OscAddress.from_cache(this.get_osc_address_string());
    }

    @Override
    public String get_osc_address_string() {
        return "/";
    }

    @Override
    public State get_state() {
        ArrayList<State> global_state = new ArrayList<State>();
        for (Server module_server : this.get_child_module_servers()) {
            global_state.add(module_server.get_state());
        }
        return new GlobalState(global_state.toArray(new State[0]));
    }
}
