package oovu.servers;

import java.util.ArrayList;
import java.util.Map;

import oovu.Binding;
import oovu.addressing.Environment;
import oovu.addressing.OscAddressNode;
import oovu.clients.MessagePasserCallback;
import oovu.messaging.MessageHandler;
import oovu.messaging.Response;
import oovu.servers.members.PropertyServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

public class ModuleServer extends Server {

    private class GetNameMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getname";
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

    public ModuleServer(Integer module_id, Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_id = module_id;
        this.attach_to_parent_server(Environment.root_server);
        this.add_message_handler(new GetNameMessageHandler());
    }

    public void acquire_name(String desired_name) {
        if (this.name != null) {
            return;
        }
        this.name = this.osc_address_node.acquire_name(desired_name);
    }
    
    public PropertyServer[] get_property_servers() {
        return null;
    }
    
}
