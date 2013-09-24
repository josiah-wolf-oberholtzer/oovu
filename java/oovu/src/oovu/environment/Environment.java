package oovu.environment;

import java.util.HashMap;
import java.util.HashSet;

import oovu.Binding;
import oovu.servers.AudioNode;
import oovu.servers.ModuleMemberNode;
import oovu.servers.ModuleNode;
import oovu.servers.RootNode;

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
    
    public static void reset() {
        Environment.modules_by_module_id.clear();
        Environment.root_node.clear();
        Environment.root_osc_address_node.clear();
        //Environment.osc_addresses.clear();
        Environment.pull_addresses.clear();
        Environment.push_addresses.clear();
        Environment.root_osc_address_node.set_node(Environment.root_node);
    }

}
