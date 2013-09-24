package oovu.environment;

import java.util.HashMap;
import java.util.HashSet;

import oovu.Binding;
import oovu.nodes.AudioNode;
import oovu.nodes.ModuleMemberNode;
import oovu.nodes.ModuleNode;
import oovu.nodes.RootNode;

import com.cycling74.max.MaxObject;

public class Environment {

    public static final HashMap<Integer, ModuleNode> modules_by_module_id = new HashMap<Integer, ModuleNode>();
    //public static final HashMap<String, Node> osc_addresses = new HashMap<String, Node>();
    public static final HashMap<String, AudioNode> pull_addresses = new HashMap<String, AudioNode>();
    public static final HashMap<String, AudioNode> push_addresses = new HashMap<String, AudioNode>();
    public static final RootNode root_node;
    public static final OscAddressNode root_osc_address_node;
    public static final HashMap<OscAddress, HashSet<Binding>> waiting_bindings = 
    	new HashMap<OscAddress, HashSet<Binding>>();
    
    static {
    	root_node = new RootNode();
    	root_osc_address_node = new OscAddressNode("");
    	root_osc_address_node.set_node(root_node);
    }
    
    public static void report() {
        MaxObject.post("OOVU ENVIRONMENT [" + Environment.root_node.get_reference_count() + "]\n");
        ModuleNode[] module_nodes = root_node.child_nodes.values().toArray(new ModuleNode[0]);
        for (ModuleNode module_node : module_nodes) {
            String message = "    [" + module_node.module_id + "]: "
                + module_node.get_name() + " ["
                + module_node.get_reference_count() + "]\n";
            MaxObject.post(message);
            ModuleMemberNode[] member_nodes = module_node.child_nodes.values().toArray(new ModuleMemberNode[0]);
            for (ModuleMemberNode member_node : member_nodes) {
                message = "        [" + module_node.module_id + "]: "
                    + member_node.get_name() + " ("
                    + member_node.getClass().getSimpleName() + " ["
                    + member_node.get_reference_count() + "])";
                message += "\n";
                MaxObject.post(message);
            }
        }
    }
    
    public static void reset() {
        Environment.modules_by_module_id.clear();
        Environment.root_node.child_nodes.clear();
        Environment.root_osc_address_node.clear();
        //Environment.osc_addresses.clear();
        Environment.pull_addresses.clear();
        Environment.push_addresses.clear();
        Environment.root_osc_address_node.set_node(Environment.root_node);
    }

}
