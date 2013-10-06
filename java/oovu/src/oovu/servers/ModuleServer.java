package oovu.servers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import oovu.Binding;
import oovu.addressing.Environment;
import oovu.addressing.OscAddressNode;
import oovu.clients.MessagePasserCallback;
import oovu.messaging.MessageHandler;
import oovu.messaging.Response;
import oovu.servers.members.MethodServer;
import oovu.servers.members.PropertyServer;
import oovu.servers.members.ReturnServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

public class ModuleServer extends Server {

    private class GetNameMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getname";
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String name = ModuleServer.this.get_name();
            if (name != null) {
                Atom[][] result = new Atom[1][];
                result[0] = Atom.newAtom(new String[] {
                    "name", name
                });
                return result;
            }
            return null;
        }
    }

    private class GetStateMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getstate";
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] state = ModuleServer.this.get_state();
            for (int i = 0, j = state.length; i < j; i++) {
                state[i] = Atom.newAtom("state", state[i]);
            }
            return state;
        }
    }

    public static ModuleServer allocate(Integer module_id) {
        boolean server_is_new = false;
        ModuleServer module_server = null;
        OscAddressNode osc_address_node = Environment.root_osc_address_node
            .get_numbered_child(module_id);
        if (osc_address_node != null) {
            Server server = osc_address_node.get_server();
            if (server != null) {
                if (ModuleServer.class.isInstance(server)) {
                    module_server = (ModuleServer) server;
                    server_is_new = false;
                } else {
                    throw new RuntimeException(
                        "Non-module server attached to numbered node.");
                }
            } else {
                module_server = new ModuleServer(module_id, null);
                module_server.attach_to_osc_address_node(osc_address_node);
                server_is_new = true;
            }
        } else {
            osc_address_node = new OscAddressNode(module_id);
            Environment.root_osc_address_node.add_child(osc_address_node);
            module_server = new ModuleServer(module_id, null);
            module_server.attach_to_osc_address_node(osc_address_node);
            server_is_new = true;
        }
        if (server_is_new) {
            Response response = module_server.generate_dumpmeta_response();
            for (Binding binding : osc_address_node.get_bindings()) {
                MaxSystem
                    .deferLow(new MessagePasserCallback(binding, response));
            }
        }
        return module_server;
    }

    public final Integer module_id;

    public ModuleServer(Atom[] arguments) {
        this(arguments[0].getInt(), Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public ModuleServer(Integer module_id, Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_id = module_id;
        this.attach_to_parent_server(Environment.root_server);
        this.add_message_handler(new GetNameMessageHandler());
        this.add_message_handler(new GetStateMessageHandler());
    }

    public void acquire_name(String desired_name) {
        if (this.name != null) {
            return;
        }
        this.name = this.osc_address_node.acquire_name(desired_name);
    }

    public List<MethodServer> get_child_method_servers() {
        ArrayList<MethodServer> method_servers = new ArrayList<MethodServer>();
        for (Server child : this.child_servers) {
            if (child instanceof MethodServer) {
                method_servers.add((MethodServer) child);
            }
        }
        Collections.sort(method_servers);
        return method_servers;
    }

    public List<PropertyServer> get_child_property_servers() {
        ArrayList<PropertyServer> property_servers = new ArrayList<PropertyServer>();
        for (Server child : this.child_servers) {
            if (child instanceof PropertyServer) {
                property_servers.add((PropertyServer) child);
            }
        }
        Collections.sort(property_servers);
        return property_servers;
    }

    public List<ReturnServer> get_child_return_servers() {
        ArrayList<ReturnServer> return_servers = new ArrayList<ReturnServer>();
        for (Server child : this.child_servers) {
            if (child instanceof ReturnServer) {
                return_servers.add((ReturnServer) child);
            }
        }
        Collections.sort(return_servers);
        return return_servers;
    }

    public Atom[][] get_state() {
        ArrayList<Atom[]> state = new ArrayList<Atom[]>();
        for (PropertyServer property : this.get_child_property_servers()) {
            state.add(Atom.newAtom(property.get_osc_address_string(),
                property.get_value()));
        }
        return state.toArray(new Atom[0][]);
    }
}
