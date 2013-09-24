package oovu.environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.servers.Server;

import org.apache.commons.lang3.StringUtils;

import com.sun.tools.javac.util.List;


public class OscAddressNode {

    private final String name;
    private final Map<String, OscAddressNode> children = 
        new HashMap<String, OscAddressNode>();
    private OscAddressNode parent = null;
    private Server node = null;
    
    public OscAddressNode(String name) {
        this.name = name;
    }
    
    public void add_child(OscAddressNode child) {
    	OscAddressNode[] parentage = this.get_parentage();
    	if (Arrays.asList(parentage).contains(child)) {
    		return;
    	}
    	if (child.get_parent() != null) {
    		child.get_parent().remove_child(child);
    	}
        this.children.put(child.name, child);
        child.parent = this;
    }
    
    public void clear() {
    	OscAddressNode[] children = this.children.values().toArray(
    		new OscAddressNode[0]);
    	for (OscAddressNode child : children) {
    		this.remove_child(child);
    	}
    }
    
    public OscAddressNode create_address(OscAddress osc_address, boolean uniquely) {
    	if (osc_address.has_wildcard_tokens || osc_address.has_parent_path_tokens) {
    		throw new RuntimeException("OSC address is ambiguous: " + osc_address);
    	}
    	OscAddressNode parent = this;
    	if (! osc_address.is_relative) {
    		parent = this.get_root();
    	}
    	OscAddressNode child = null;
    	for (int i = 0; i < osc_address.address_items.length; i++) {
    		String name = osc_address.address_items[i];
    		if ((i == (osc_address.address_items.length - 1)) && uniquely) {
    			name = parent.find_unique_name(name);
    		}
    		child = parent.get_child(name);
    		if (child == null) {
    			child = new OscAddressNode(name);
    			parent.add_child(child);
    		}
    		parent = child;
    	}
    	return child;
    }
    
    public String find_unique_name(String desired_name) {
        if (!this.children.containsKey(desired_name)) {
            return desired_name;
        }
        Integer counter = 1;
        String acquired_name = desired_name + '.' + counter.toString();
        while (this.children.containsKey(acquired_name)) {
            counter += 1;
            acquired_name = desired_name + '.' + counter.toString();
        }
        return acquired_name;
    }
    
    public OscAddressNode get_child(String name) {
    	return this.children.get(name);
    }
    
    public String get_name() {
    	return this.name;
    }
    
    public Server get_node() {
        return this.node;
    }
    
    public String get_osc_address() {
    	ArrayList<String> names = new ArrayList<String>();
    	for (OscAddressNode node : this.get_parentage()) {
    		names.add(node.name);
    	}
    	List<String> reversed_names = List.from(names.toArray(new String[0]));
    	reversed_names = reversed_names.reverse();
    	return StringUtils.join(reversed_names, "/");
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
    
    public int get_reference_count() {
    	return this.children.size();
    }
    
    public OscAddressNode get_root() {
    	OscAddressNode[] parentage = this.get_parentage();
    	return parentage[parentage.length - 1];
    }
    
    public String[] get_summary_pieces() {
    	ArrayList<String> pieces = new ArrayList<String>();
    	if (this.name != "") {
    		pieces.add("/" + this.name);
    	}
    	for (OscAddressNode child : this.children.values()) {
    		for (String piece : child.get_summary_pieces()) {
    			if (this.name != "") {
    				pieces.add("/" + this.name + piece);
    			} else {
    				pieces.add(piece);
    			}
    		}
    	}
    	String[] sorted_pieces = pieces.toArray(new String[0]);
    	Arrays.sort(sorted_pieces);
    	return sorted_pieces;
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
    
    public boolean matches(String token) {
    	if (token.equals("*") || this.name.equals(token)) {
    		return true;
    	} else if (this.name.contains(".") && token.contains(".")) {
    		String[] token_parts = token.split("\\.");
    		String[] name_parts = this.name.split("\\.");
    		boolean[] matches = new boolean[]{ false, false };
    		if (token_parts[0].equals("*") || token_parts[0].equals(name_parts[0])) {
    			matches[0] = true;
    		}
    		if (token_parts[1].equals("*") || token_parts[1].equals(name_parts[1])) {
    			matches[1] = true;
    		}
    		if (matches[0] && matches[1]) {
    			return true;
    		}
    	}
    	return false;
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
    
    public void remove_child(OscAddressNode child) {
        this.children.remove(child.name);
        child.parent = null;
    }
    
    public Set<OscAddressNode> search(OscAddress osc_address) {
    	Set<OscAddressNode> old_cursors = new HashSet<OscAddressNode>();
    	Set<OscAddressNode> new_cursors = new HashSet<OscAddressNode>();
    	if (osc_address.is_relative) {
    		old_cursors.add(this);
    	} else {
    		old_cursors.add(this.get_root());
    	}
    	if (osc_address.address_items.length < 1) {
    		return old_cursors;
    	}
    	for (String current_address_item : osc_address.address_items) {
    		new_cursors.clear();
    		for (OscAddressNode current_cursor : old_cursors) {
    			if (current_address_item.equals("..") && 
    				current_cursor.get_parent() != null) {
    				new_cursors.add(current_cursor.get_parent());
    			} else if (current_address_item.contains("*")) {
    				for (OscAddressNode child : current_cursor.children.values()) {
    					if (child.matches(current_address_item)) {
    						new_cursors.add(child);
    					}
    				}
    			} else {
    				OscAddressNode child = current_cursor.get_child(current_address_item);
    				if (child != null) {
    					new_cursors.add(child);
    				}
    			}
    		}
    		old_cursors.clear();
    		old_cursors.addAll(new_cursors);
    	}
    	return new_cursors;
    }
    
    public void set_node(Server node) {
        this.node = node;
    }
    
    @Override
	public String toString() {
    	return this.getClass().getSimpleName() + "(\"" + 
    		this.get_osc_address() + "\")";
    }
}
