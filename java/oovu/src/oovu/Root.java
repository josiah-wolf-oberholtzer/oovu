package oovu;

import oovu.addressing.Environment;
import oovu.clients.ServerClient;

public class Root extends ServerClient {

    public Root() {
        this.declareIO(2, 2);
        this.server = Environment.root_server;
        this.server.server_clients.add(this);
    }
}
