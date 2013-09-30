package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.messaging.MessageHandler;
import oovu.servers.members.MethodServer;
import oovu.servers.members.PropertyServer;
import oovu.servers.members.PullServer;
import oovu.servers.members.PushServer;
import oovu.servers.members.ReturnServer;

import com.cycling74.max.Atom;

public abstract class ModuleMemberServer extends Server {

    private class GetModuleNameMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getmodulename";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            if (ModuleMemberServer.this.parent_server.get_name() != null) {
                result[0] = Atom.newAtom(new String[] {
                    "modulename",
                    ModuleMemberServer.this.parent_server.get_name()
                });
            } else {
                result[0] = Atom.newAtom(new String[] {
                    "modulename"
                });
            }
            return result;
        }
    }

    public static final Map<String, Class<?>> member_nodes_by_label;
    static {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("MethodNode", MethodServer.class);
        map.put("PropertyNode", PropertyServer.class);
        map.put("PullNode", PullServer.class);
        map.put("PushNode", PushServer.class);
        map.put("ReturnNode", ReturnServer.class);
        member_nodes_by_label = Collections.unmodifiableMap(map);
    }

    public static ModuleMemberServer allocate_from_label(
        String label,
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        Class<?> member_node_class = ModuleMemberServer.member_nodes_by_label
            .get(label);
        if (member_node_class == null) {
            member_node_class = PropertyServer.class;
        }
        ModuleServer module_server = ModuleServer.allocate(module_id);
        OscAddress osc_address = OscAddress.from_cache(desired_name);
        if (osc_address.has_parent_path_tokens
            || osc_address.has_wildcard_tokens || !osc_address.is_relative) {
            throw new RuntimeException("Bad child address: " + desired_name);
        }
        ModuleMemberServer member_server = null;
        OscAddressNode osc_address_node = module_server.get_osc_address_node()
            .search_for_one(osc_address);
        if (osc_address_node == null) {
            // address doesn't exist
            member_server = ModuleMemberServer.allocate_new_from_label(
                module_server, label, module_id, argument_list);
            osc_address_node = module_server.get_osc_address_node()
                .create_address(osc_address, true);
            member_server.attach_to_osc_address_node(osc_address_node);
        } else if (osc_address_node.get_server() == null) {
            // address does exist but no server is attached
            member_server = ModuleMemberServer.allocate_new_from_label(
                module_server, label, module_id, argument_list);
            member_server.attach_to_osc_address_node(osc_address_node);
        } else {
            // address exists and a server is already attached
            Server current_member_server = osc_address_node.get_server();
            if (current_member_server.getClass() == member_node_class) {
                // server is of the desired type
                member_server = (ModuleMemberServer) current_member_server;
            } else {
                // server is not of the desired type, so acquire a new address
                member_server = ModuleMemberServer.allocate_new_from_label(
                    module_server, label, module_id, argument_list);
                osc_address_node = module_server.get_osc_address_node()
                    .create_address(osc_address, true);
                member_server.attach_to_osc_address_node(osc_address_node);
            }
        }
        return member_server;
    }

    private static ModuleMemberServer allocate_new_from_label(
        ModuleServer module_server,
        String label,
        Integer module_id,
        Atom[] argument_list) {
        Class<?> member_node_class = ModuleMemberServer.member_nodes_by_label
            .get(label);
        Map<String, Atom[]> argument_map = Server
            .process_atom_arguments(argument_list);
        ModuleMemberServer new_member_node = null;
        try {
            new_member_node = (ModuleMemberServer) member_node_class
                .getDeclaredConstructor(ModuleServer.class, Map.class)
                .newInstance(module_server, argument_map);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (new_member_node == null) {
            new_member_node = new PropertyServer(module_server, argument_map);
        }
        return new_member_node;
    }

    public ModuleMemberServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.attach_to_parent_server(module_server);
        this.add_message_handler(new GetModuleNameMessageHandler());
    }

    abstract public ModuleMemberServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map);
}
