package oovu;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;
import oovu.nodes.PullNode;
import oovu.proxies.AudioNodeProxy;

public class PullSource extends AudioNodeProxy {

    public PullSource(Atom[] arguments) {
        if (arguments.length < 2) {
            MaxObject.bail("Bad arguments.");
        } else if (!arguments[0].isInt()) {
            MaxObject.bail("Bad arguments.");
        }
        this.declareIO(2, 1);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.node = PullNode.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.node.node_proxies.add(this);
    }

}