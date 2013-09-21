package oovu;

import com.cycling74.max.MaxObject;
import oovu.nodes.Node;
import oovu.proxies.NodeProxy;

public class Binding extends NodeProxy {

    @Override
    public void notifyDeleted() {
        Node node = this.get_node();
        if (node == null) {
            return;
        }
        node.bindings.remove(this);
        MaxObject.post("DELETING: " + this.toString() + " / " + node.toString()
            + "\n");
    }
    
}
