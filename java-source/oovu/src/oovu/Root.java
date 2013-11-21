package oovu;

import oovu.adapters.GenericMaxAdapter;
import oovu.addresses.Environment;
import oovu.clients.ServerClient;

public class Root extends ServerClient {

    public Root() {
        this.declareIO(2, 2);
        this.max_adapter = new GenericMaxAdapter(this);
        this.server = Environment.root_server;
        this.server.server_clients.add(this);
        this.server.make_deferred_request(this, "dumpmeta", null);
    }
}
