package oovu.environment;

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
    
    public boolean is_empty() {
        if (this.node == null && this.children.size() == 0) {
            return true;
        }
        return false;
    }
    
    public void prune() {
        OscAddressNode child = this;
        OscAddressNode parent = this.parent;
        while (child.is_empty() && parent != null) {
            parent.remove_child(child);
            child = parent;
            parent = child.parent;
        }
    }
    
}
