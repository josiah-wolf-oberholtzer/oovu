package oovu;

import oovu.clients.ServerClient;
import oovu.environment.Environment;

import com.cycling74.max.Atom;


public class Root extends ServerClient {

    public Root() {
        this.declareIO(2, 1);
        this.server = Environment.root_server;
        this.server.node_proxies.add(this);
    }

    @Override
    public void output_value_response_payload(Atom[] payload) {
        this.outlet(0, payload);
    }

}
