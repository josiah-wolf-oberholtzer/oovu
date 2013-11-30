package oovu;

import oovu.adapters.GenericMaxAdapter;
import oovu.clients.ServerClient;
import oovu.servers.ModuleServer;

import com.cycling74.max.Atom;

public class Module extends ServerClient {
    public Module(Atom[] arguments) {
        this.declareIO(2, 2);
        this.check_arguments(arguments);
        if (arguments[0].isString() && arguments[0].getString().equals("#1")) {
            return;
        }
        this.max_adapter = new GenericMaxAdapter(this);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        ModuleServer module_server = ModuleServer.allocate(module_id);
        module_server.acquire_name(desired_name);
        this.attach_to_server(module_server);
        this.server.make_deferred_request(this, "dumpmeta", null);
    }
}
