package oovu;

import oovu.proxies.AudioNodeProxy;
import oovu.servers.PullServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

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
        this.node = PullServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.node.node_proxies.add(this);
    }

}
