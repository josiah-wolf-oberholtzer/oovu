package oovu.addressing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.Binding;
import oovu.servers.Server;

import org.apache.commons.lang3.StringUtils;

import com.sun.tools.javac.util.List;

public class OscAddressNode {

    public static
        String
        find_unique_name(String desired_name, Set<String> names) {
        if (!names.contains(desired_name)) {
            return desired_name;
        }
        Integer counter = 1;
        String acquired_name = desired_name + '.' + counter.toString();
        while (names.contains(acquired_name)) {
            counter += 1;
            acquired_name = desired_name + '.' + counter.toString();
        }
        return acquired_name;
    }

    private String name;
    private Integer number;
    private final Set<Binding> bindings = new HashSet<Binding>();
    private final Map<String, OscAddressNode> named_children = new HashMap<String, OscAddressNode>();
    private final Map<Integer, OscAddressNode> numbered_children = new HashMap<Integer, OscAddressNode>();
    private OscAddressNode parent = null;
    private Server server = null;

    public OscAddressNode(Integer number) {
        this(null, number);
    }
    
    public OscAddressNode(String name) {
        this(name, null);
    }

    private OscAddressNode(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String acquire_name(String desired_name) {
        if (this.name != null) {
            if (this.name == desired_name) {
                return this.name;
            }
            throw new RuntimeException("Already has name.");
        }
        if (this.parent == null) {
            this.name = desired_name;
            return desired_name;
        }
        Set<String> names = this.parent.named_children.keySet();
        String acquired_name = OscAddressNode.find_unique_name(desired_name,
            names);
        this.name = acquired_name;
        this.parent.named_children.put(acquired_name, this);
        return acquired_name;
    }

    public void merge_with(OscAddressNode other) {
        
    }
    
    public void add_child(OscAddressNode child) {
        OscAddressNode[] parentage = this.get_parentage();
        if (Arrays.asList(parentage).contains(child)) {
            return;
        }
        if (child.get_parent() != null) {
            child.get_parent().remove_child(child);
        }
        if (child.name != null) {
            this.named_children.put(child.name, child);
        }
        if (child.number != null) {
            this.numbered_children.put(child.number, child);
        }
        child.parent = this;
    }

    public void clear() {
        OscAddressNode[] children = this.named_children.values().toArray(
            new OscAddressNode[0]);
        for (OscAddressNode child : children) {
            this.remove_child(child);
        }
    }

    public OscAddressNode create_address(
        OscAddress osc_address,
        boolean uniquely) {
        if (osc_address.has_wildcard_tokens
            || osc_address.has_parent_path_tokens) {
            throw new RuntimeException("OSC address is ambiguous: "
                + osc_address);
        }
        OscAddressNode parent = this;
        if (!osc_address.is_relative) {
            parent = this.get_root();
        }
        OscAddressNode child = null;
        for (int i = 0; i < osc_address.address_items.length; i++) {
            String name = osc_address.address_items[i];
            if ((i == (osc_address.address_items.length - 1)) && uniquely) {
                Set<String> names = parent.named_children.keySet();
                name = OscAddressNode.find_unique_name(name, names);
            }
            child = parent.get_named_child(name);
            if (child == null) {
                child = new OscAddressNode(name, null);
                parent.add_child(child);
            }
            parent = child;
        }
        return child;
    }

    public Set<Binding> get_bindings() {
        return Collections.unmodifiableSet(this.bindings);
    }

    public String get_name() {
        return this.name;
    }

    public OscAddressNode get_named_child(String name) {
        return this.named_children.get(name);
    }

    public int get_number() {
        return this.number;
    }

    public OscAddressNode get_numbered_child(Integer number) {
        return this.numbered_children.get(number);
    }

    public String get_osc_address() {
        ArrayList<String> names = new ArrayList<String>();
        for (OscAddressNode osc_address_node : this.get_parentage()) {
            names.add(osc_address_node.name);
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
        Set<OscAddressNode> children = new HashSet<OscAddressNode>();
        children.addAll(this.named_children.values());
        children.addAll(this.numbered_children.values());
        return children.size();
    }

    public OscAddressNode get_root() {
        OscAddressNode[] parentage = this.get_parentage();
        return parentage[parentage.length - 1];
    }

    public Server get_server() {
        return this.server;
    }

    public String[] get_summary_pieces() {
        ArrayList<String> pieces = new ArrayList<String>();
        if (this.name != "") {
            pieces.add("/" + this.name);
        }
        for (OscAddressNode child : this.named_children.values()) {
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
        if ((this.server == null) && (this.named_children.size() == 0)) {
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
            boolean[] matches = new boolean[] {
                false, false
            };
            if (token_parts[0].equals("*")
                || token_parts[0].equals(name_parts[0])) {
                matches[0] = true;
            }
            if (token_parts[1].equals("*")
                || token_parts[1].equals(name_parts[1])) {
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
        if (child == null) {
            return;
        }
        if (child.name != null) {
            if (this.named_children.get(child.name) == child) {
                this.named_children.remove(child.name);
            }
        }
        if (child.number != null) {
            if (this.numbered_children.get(child.number) == child) {
                this.numbered_children.remove(child.number);
            }
        }
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
                if (current_address_item.equals("..")
                    && (current_cursor.get_parent() != null)) {
                    new_cursors.add(current_cursor.get_parent());
                } else if (current_address_item.contains("*")) {
                    for (OscAddressNode child : current_cursor.named_children
                        .values()) {
                        if (child.matches(current_address_item)) {
                            new_cursors.add(child);
                        }
                    }
                } else {
                    OscAddressNode child = current_cursor
                        .get_named_child(current_address_item);
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

    public void set_server(Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(\"" + this.get_osc_address()
            + "\")";
    }
}
