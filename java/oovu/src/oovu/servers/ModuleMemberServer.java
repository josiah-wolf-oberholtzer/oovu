package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MessageHandler;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class ModuleMemberServer extends Server {

    private class GetModuleNameMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getmodulename";
        }

        @Override
        public Atom[][] run(Server server, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            AttributeServer attribute_node = (AttributeServer) server;
            result[0] = Atom.newAtom(new String[] { "modulename",
                attribute_node.module_node.get_name() });
            return result;
        }
    }

    public final ModuleServer module_node;
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

    public static ModuleMemberServer allocate_from_label(String label,
        Integer module_id, String desired_name, Atom[] argument_list) {
        Class<?> member_node_class = ModuleMemberServer.member_nodes_by_label
            .get(label);
        if (member_node_class == null) {
            member_node_class = PropertyServer.class;
        }
        ModuleServer module_node = ModuleServer.allocate(module_id);
        if (module_node.child_nodes.containsKey(desired_name)) {
            ModuleMemberServer current_member_node = (ModuleMemberServer) module_node.child_nodes
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
                .newInstance(module_node, argument_map);
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
            new_member_node = new PropertyServer(module_node, argument_map);
        }
        new_member_node.register_name(desired_name);
        return new_member_node;
    }

    public ModuleMemberServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_node = module_node;
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
        this.module_node.deallocate_if_necessary();
    }

    @Override
    public String get_osc_address() {
        if ((this.module_node == null) || (this.module_node.name == null)
            || (this.name == null)) {
            return null;
        }
        return '/' + this.module_node.name + '/' + this.name;
    }

    @Override
    public Server get_parent_node() {
        return this.module_node;
    }

    @Override
    public int get_reference_count() {
        return this.node_proxies.size();
    }

    abstract public ModuleMemberServer new_instance(Integer module_id,
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
        String acquired_name = Server.find_unique_name(desired_name,
            this.module_node.child_nodes.keySet());
        this.name = acquired_name;
        this.module_node.child_nodes.put(acquired_name, this);
        this.register_at_osc_address();
    }

    @Override
    public void unregister_name() {
        this.unregister_from_osc_address();
        this.module_node.child_nodes.remove(this.name);
        this.name = null;
    }

}
