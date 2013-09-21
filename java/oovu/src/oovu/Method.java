package oovu;

import com.cycling74.max.*;
import oovu.nodes.*;
import oovu.proxies.NodeProxy;

public class Method extends NodeProxy {

    public Method(Atom[] arguments) {
        if (arguments.length < 2) {
            MaxObject.bail("Bad arguments.");
        } else if (!arguments[0].isInt()) {
            MaxObject.bail("Bad arguments.");
        }
        this.declareIO(2, 2);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.node = MethodNode.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.node.node_proxies.add(this);
    }

}
