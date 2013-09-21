package oovu.environment;

import java.util.*;
import oovu.nodes.*;
import com.cycling74.max.*;

public class Environment {

    public static final HashMap<Integer, ModuleNode> modules_by_module_id = new HashMap<Integer, ModuleNode>();
    public static final HashMap<String, Node> osc_addresses = new HashMap<String, Node>();
    public static final HashMap<String, AudioNode> pull_addresses = new HashMap<String, AudioNode>();
    public static final HashMap<String, AudioNode> push_addresses = new HashMap<String, AudioNode>();
    public static final RootNode root_node = new RootNode();
    public static final OscAddressNode root_osc_address_node = new OscAddressNode("");
    
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
        Environment.osc_addresses.clear();
        Environment.pull_addresses.clear();
        Environment.push_addresses.clear();
    }
    
    public static OscAddressNode create_address(String[] osc_address_parts) {
    	if (! OscAddress.all_are_valid_names(osc_address_parts)) {
    		return null;
    	}
    	OscAddressNode parent_node = Environment.root_osc_address_node;
    	OscAddressNode child_node = Environment.root_osc_address_node;
    	for (String osc_address_part : osc_address_parts) {
    		child_node = parent_node.get_child(osc_address_part);
    		if (child_node == null) {
    			child_node = new OscAddressNode(osc_address_part);
    			parent_node.add_child(child_node);
    		}
    		parent_node = child_node;
    	}
    	return child_node;
    }
    
    public static void prune_address(OscAddressNode osc_address_node) {
    	osc_address_node.prune();
    }
    
    public static OscAddressNode find_address(String[] osc_address_parts) {
    	OscAddressNode current_node = Environment.root_osc_address_node;
    	for (String osc_address_part : osc_address_parts) {
    		current_node = current_node.get_child(osc_address_part);
    		if (current_node == null) {
    			return null;
    		}
    	}
    	return current_node;
    }

}
