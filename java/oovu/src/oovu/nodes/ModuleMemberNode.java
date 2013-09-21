package oovu.nodes;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import oovu.environment.InterfaceHandler;
import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public abstract class ModuleMemberNode extends Node {

    private class GetModuleNameInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getmodulename";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            AttributeNode attribute_node = (AttributeNode) context;
            result[0] = Atom.newAtom(new String[] { "modulename",
                attribute_node.module_node.get_name() });
            return result;
        }
    }

    public final ModuleNode module_node;
    public static final Map<String, Class<?>> member_nodes_by_label;

    public ModuleMemberNode(ModuleNode module_node,
        Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_node = module_node;
        for (String key : argument_map.keySet().toArray(
            new String[argument_map.size()])) {
            MaxObject.post(key + ": " + Atom.toOneString(argument_map.get(key))
                + "\n");
        }
        this.add_interface_handler(new GetModuleNameInterfaceHandler());
    }

    static {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("MethodNode", MethodNode.class);
        map.put("PropertyNode", PropertyNode.class);
        map.put("PullNode", PullNode.class);
        map.put("PushNode", PushNode.class);
        map.put("ReturnNode", ReturnNode.class);
        member_nodes_by_label = Collections.unmodifiableMap(map);
    }

    public static ModuleMemberNode allocate_from_label(String label,
        Integer module_id, String desired_name, Atom[] argument_list) {
        Class<?> member_node_class = ModuleMemberNode.member_nodes_by_label
            .get(label);
        if (member_node_class == null) {
            member_node_class = PropertyNode.class;
        }
        ModuleNode module_node = ModuleNode.allocate(module_id);
        if (module_node.child_nodes.containsKey(desired_name)) {
            ModuleMemberNode current_member_node = (ModuleMemberNode) module_node.child_nodes
                .get(desired_name);
            if (current_member_node.getClass() == member_node_class) {
                return current_member_node;
            }
        }
        Map<String, Atom[]> argument_map = Node
            .process_atom_arguments(argument_list);
        ModuleMemberNode new_member_node = null;
        try {
            new_member_node = (ModuleMemberNode) member_node_class
                .getDeclaredConstructor(ModuleNode.class, Map.class)
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
            new_member_node = new PropertyNode(module_node, argument_map);
        }
        new_member_node.register_name(desired_name);
        return new_member_node;
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
    public Node get_parent_node() {
        return this.module_node;
    }

    @Override
    public Integer get_reference_count() {
        return this.node_proxies.size();
    }

    abstract public ModuleMemberNode new_instance(Integer module_id,
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
        String acquired_name = Node.find_unique_name(desired_name,
            this.module_node.child_nodes.keySet());
        this.name = acquired_name;
        this.module_node.child_nodes.put(acquired_name, this);
        this.register_osc_address();
    }

    @Override
    public void unregister_name() {
        this.unregister_osc_address();
        this.module_node.child_nodes.remove(this.name);
        this.name = null;
    }

}
