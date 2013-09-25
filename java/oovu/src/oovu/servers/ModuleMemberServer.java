package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.environment.OscAddressNode;
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
            if (ModuleMemberServer.this.module_server.get_name() != null) {
                result[0] = Atom.newAtom(new String[] {
                    "modulename", ModuleMemberServer.this.module_server.get_name()
                });
            } else {
                result[0] = Atom.newAtom(new String[] { "modulename" });
            }
            return result;
        }
    }

    public final ModuleServer module_server;
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
        if (module_server.child_servers.containsKey(desired_name)) {
            ModuleMemberServer current_member_node = (ModuleMemberServer) module_server.child_servers
                .get(desired_name);
            if (current_member_node.getClass() == member_node_class) {
                return current_member_node;
            }
        }
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
        new_member_node.register_name(desired_name);
        return new_member_node;
    }

    public ModuleMemberServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_server = module_server;
        for (String key : argument_map.keySet().toArray(
            new String[argument_map.size()])) {
            MaxObject.post(key + ": " + Atom.toOneString(argument_map.get(key))
                + "\n");
        }
        this.add_message_handler(new GetModuleNameMessageHandler());
    }

    @Override
    public void deallocate() {
        this.unregister_name();
        this.module_server.deallocate_if_necessary();
    }

    @Override
    public int get_reference_count() {
        return this.server_clients.size();
    }

    abstract public ModuleMemberServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map);

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
            this.module_server.child_servers.keySet());
        this.name = acquired_name;
        this.module_server.child_servers.put(acquired_name, this);
        this.register_at_osc_address();
    }

    @Override
    public void unregister_name() {
        this.unregister_from_osc_address();
        this.module_server.child_servers.remove(this.name);
        this.name = null;
    }
}
