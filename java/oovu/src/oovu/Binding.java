package oovu;

import java.util.HashSet;
import java.util.Set;

import oovu.environment.Environment;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;
import oovu.nodes.Node;
import oovu.proxies.NodeProxy;

import com.cycling74.max.MaxObject;
import com.google.common.collect.Sets;

public class Binding extends NodeProxy {

	protected OscAddress target_osc_address = null;

    public void bind(OscAddress osc_address) {
    	if (osc_address.has_wildcard_tokens) {
    		MaxObject.ouch("Can't bind to wildcard OSC address: " + osc_address);
    	}
    	this.unbind();
    	this.target_osc_address = osc_address;
    	// reregister as waiting for new address
    	Set<OscAddressNode> target_osc_address_node_set = Environment.root_osc_address_node.search(
    		this.target_osc_address);
    	if (target_osc_address_node_set == null || 
    		target_osc_address_node_set.size() == 0) {
    		return;
    	}
    	OscAddressNode target_osc_address_node = target_osc_address_node_set.toArray(
    		new OscAddressNode[0])[0];
    	Node target_node = target_osc_address_node.get_node();
    	if (target_node == null) {
    		return;
    	}
    	this.unregister_as_waiting();
        // unregister as waiting binding 
    	target_node.bindings.add(this);
    	this.node = target_node;
    }
    
    @Override
    public void notifyDeleted() {
    	this.unbind();
        MaxObject.post("DELETING: " + this.toString() + " / " + node.toString()
            + "\n");
    }
    
    protected void register_as_waiting() {
    	if (Environment.waiting_bindings.containsKey(this.target_osc_address)) {
    		Environment.waiting_bindings.get(this.target_osc_address).add(this);
    	} else {
    		HashSet<Binding> bindings = Sets.newHashSet(this);
    		Environment.waiting_bindings.put(this.target_osc_address, bindings);
    	}
    }
    
    public void unbind() {
    	Node node = this.get_node();
    	if (node != null) {
    		node.bindings.remove(this);
    		this.node = null;
    	}
    }
    
    protected void unregister_as_waiting() {
    	
    }
}
