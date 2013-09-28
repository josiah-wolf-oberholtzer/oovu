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

import org.apache.commons.lang3.ArrayUtils;
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
        if (desired_name == this.name) {
            System.out.print("A");
            return desired_name;
        } else if ((desired_name == null)
            || ((this.name != null) && (this.server != null))) {
            throw new RuntimeException();
        }
        if (this.parent == null) {
            System.out.print("B");
            this.name = desired_name;
            return desired_name;
        } else if (!this.parent.named_children.containsKey(desired_name)) {
            System.out.print("C");
            this.name = desired_name;
            this.parent.named_children.put(desired_name, this);
            return desired_name;
        } else if (this.parent.named_children.get(desired_name).get_server() == null) {
            System.out.print("D");
            this.parent.named_children.get(desired_name).merge_with(this);
            return desired_name;
        } else {
            System.out.print("E");
            Set<String> names = this.parent.named_children.keySet();
            String acquired_name = OscAddressNode.find_unique_name(
                desired_name, names);
            this.name = acquired_name;
            this.parent.named_children.put(acquired_name, this);
            return acquired_name;
        }
    }

    public void add_binding(Binding binding) {
        this.bindings.add(binding);
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
        for (OscAddressNode child : this.get_all_children()) {
            this.remove_child(child);
        }
        if (this.parent != null) {
            this.parent.remove_child(this);
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

    public Set<OscAddressNode> get_all_children() {
        Set<OscAddressNode> all_children = new HashSet<OscAddressNode>();
        all_children.addAll(this.named_children.values());
        all_children.addAll(this.numbered_children.values());
        return all_children;
    }

    public Set<Binding> get_bindings() {
        return Collections.unmodifiableSet(this.bindings);
    }

    public String get_debug_piece() {
        StringBuilder string_builder = new StringBuilder();
        string_builder.append("<Node ");
        if (this.name != null) {
            string_builder.append("\"" + this.name + "\"");
        } else {
            string_builder.append("null");
        }
        string_builder.append(":");
        if (this.number != null) {
            string_builder.append(this.number);
        } else {
            string_builder.append("null");
        }
        if ((0 < this.bindings.size()) || (this.server != null)) {
            string_builder.append(" (");
            if (0 < this.bindings.size()) {
                string_builder.append("bindings: ");
                string_builder.append(this.bindings.size());
                if (this.server != null) {
                    string_builder.append(", ");
                }
            }
            if (this.server != null) {
                string_builder.append("server: "
                    + this.server.getClass().getSimpleName());
            }
            string_builder.append(")");
        }
        string_builder.append(">");
        return string_builder.toString();
    }

    public String[] get_debug_pieces() {
        ArrayList<String> pieces = new ArrayList<String>();
        pieces.add(this.get_debug_piece());
        for (OscAddressNode child : this.get_all_children()) {
            for (String child_piece : child.get_debug_pieces()) {
                pieces.add("    " + child_piece);
            }
        }
        return pieces.toArray(new String[0]);
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
        int count = this.get_all_children().size();
        count += this.bindings.size();
        if (this.server != null) {
            count += 1;
        }
        return count;
    }

    public String get_relative_osc_address(
        OscAddressNode relative_osc_address_node) {
        OscAddressNode[] source_parentage = this.get_parentage();
        OscAddressNode[] relative_parentage = relative_osc_address_node
            .get_parentage();
        ArrayUtils.reverse(source_parentage);
        ArrayUtils.reverse(relative_parentage);
        int counter = 0;
        while ((counter < source_parentage.length)
            && (counter < relative_parentage.length)
            && (source_parentage[counter] == relative_parentage[counter])) {
            counter += 1;
        }
        StringBuilder string_builder = new StringBuilder();
        while (counter < source_parentage.length) {
            string_builder.append("/");
            string_builder.append(source_parentage[counter].get_name());
            counter += 1;
        }
        return string_builder.toString();
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

    public boolean is_named() {
        return this.name != null;
    }

    public boolean is_numbered() {
        return this.number != null;
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

    public void merge_with(OscAddressNode other) {
        this.number = other.number;
        if (other.server != null) {
            other.server.attach_to_osc_address_node(this);
        }
        for (Binding binding : other.bindings) {
            binding.attach(this);
        }
        for (String child_name : other.named_children.keySet()) {
            OscAddressNode child = other.get_named_child(child_name);
            if (this.named_children.containsKey(child_name)) {
                this.named_children.get(child_name).merge_with(child);
            } else {
                this.add_child(child);
            }
        }
        this.numbered_children.putAll(other.numbered_children);
    }

    private void prune() {
        OscAddressNode[] parentage = this.get_parentage();
        parentage = Arrays.copyOf(parentage, parentage.length - 1);
        for (OscAddressNode osc_address_node : parentage) {
            if (!osc_address_node.is_empty()) {
                break;
            }
            osc_address_node.clear();
        }
    }

    public void prune_if_necessary() {
        if (0 == this.get_reference_count()) {
            this.prune();
        }
    }

    public void relinquish_name() {
        if ((this.name != null) && (this.parent != null)) {
            this.parent.named_children.remove(this.name);
        }
        this.name = null;
    }

    public void relinquish_number() {
        if ((this.number != null) && (this.parent != null)) {
            this.parent.numbered_children.remove(this.number);
        }
        this.number = null;
    }

    public void remove_binding(Binding binding) {
        this.bindings.remove(binding);
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
        this.prune_if_necessary();
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

    public OscAddressNode search_for_one(OscAddress osc_address) {
        if (osc_address.has_wildcard_tokens) {
            return null;
        }
        Set<OscAddressNode> search_results = this.search(osc_address);
        if (0 == search_results.size()) {
            return null;
        } else {
            return search_results.toArray(new OscAddressNode[0])[0];
        }
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
