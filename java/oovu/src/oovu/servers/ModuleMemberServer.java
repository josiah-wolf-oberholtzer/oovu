package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MessageHandler;
import oovu.servers.members.MethodServer;
import oovu.servers.members.PropertyServer;
import oovu.servers.members.PullServer;
import oovu.servers.members.PushServer;
import oovu.servers.members.ReturnServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

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
        // if (module_server.named_child_servers.containsKey(desired_name)) {
        // ModuleMemberServer current_member_node =
        // (ModuleMemberServer) module_server.named_child_servers
        // .get(desired_name);
        // if (current_member_node.getClass() == member_node_class) {
        // return current_member_node;
        // }
        // }
        return ModuleMemberServer.allocate_new_from_label(module_server, label,
            module_id, argument_list);
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
        for (String key : argument_map.keySet().toArray(
            new String[argument_map.size()])) {
            MaxObject.post(key + ": " + Atom.toOneString(argument_map.get(key))
                + "\n");
        }
        this.add_message_handler(new GetModuleNameMessageHandler());
    }

    @Override
    public void deallocate() {
        Server parent_server = this.get_parent_server();
        parent_server.deallocate_if_necessary();
        this.clear();
    }

    @Override
    public int get_reference_count() {
        return this.server_clients.size();
    }

    abstract public ModuleMemberServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map);
}
