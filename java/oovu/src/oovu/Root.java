package oovu;

import com.cycling74.max.Atom;
import oovu.environment.Environment;
import oovu.proxies.NodeProxy;


public class Root extends NodeProxy {

    public Root() {
        this.declareIO(2, 1);
        this.node = Environment.root_node;
        this.node.node_proxies.add(this);
    }

    @Override
    public void output_value_response_payload(Atom[] payload) {
        this.outlet(0, payload);
    }

}
