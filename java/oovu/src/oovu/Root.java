package oovu;

import oovu.addressing.Environment;
import oovu.clients.ServerClient;
import oovu.maxadapters.GenericMaxAdapter;

public class Root extends ServerClient {

    public Root() {
        this.declareIO(2, 2);
        this.max_adapter = new GenericMaxAdapter(this);
        this.server = Environment.root_server;
        this.server.server_clients.add(this);
    }
}
