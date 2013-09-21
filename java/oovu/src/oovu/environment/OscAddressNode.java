package oovu.environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import oovu.nodes.Node;


public class OscAddressNode {

    public final String name;
    private final Map<String, OscAddressNode> children = 
        new HashMap<String, OscAddressNode>();
    private OscAddressNode parent = null;
    private Node node = null;
    
    public OscAddressNode(String name) {
        this.name = name;
    }
    
    public void add_child(OscAddressNode child) {
        this.children.put(child.name, child);
        child.parent = this;
    }
    
    public void clear() {
    	this.children.clear();
    }
    
    public OscAddressNode get_child(String name) {
    	return this.children.get(name);
    }
    
    public void remove_child(OscAddressNode child) {
        this.children.remove(child.name);
        child.parent = null;
    }
    
    public void set_node(Node node) {
        this.node = node;
    }
    
    public Node get_node() {
        return this.node;
    }
    
    public OscAddressNode get_parent() {
        return this.parent;
    }
    
    public OscAddressNode[] get_parentage() {
    	ArrayList<OscAddressNode> parentage = new ArrayList<OscAddressNode>();
    	OscAddressNode child = this;
    	OscAddressNode parent = this.parent;
    	parentage.add(child);
    	while (parent != null) {
    		parentage.add(parent);
            child = parent;
            parent = child.parent;
    	}
    	return parentage.toArray(new OscAddressNode[0]);
    }
    
    public OscAddressNode get_root() {
    	OscAddressNode[] parentage = this.get_parentage();
    	return parentage[parentage.length - 1];
    }
    
    public boolean is_empty() {
        if (this.node == null && this.children.size() == 0) {
            return true;
        }
        return false;
    }
    
    public boolean is_in_environment() {
    	return this.get_root() == Environment.root_osc_address_node;
    }
    
    public int get_reference_count() {
    	return this.children.size();
    }
    
    public void prune() {
    	OscAddressNode[] parentage = this.get_parentage();
    	parentage = Arrays.copyOf(parentage, parentage.length - 1);
    	for (OscAddressNode osc_address_node : parentage) {
    		if (!osc_address_node.is_empty()) {
    			break;
    		}
    		osc_address_node.get_parent().remove_child(osc_address_node);
    	}
    }
    
}
