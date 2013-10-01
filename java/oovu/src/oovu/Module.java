package oovu;

import oovu.clients.ServerClient;
import oovu.servers.ModuleServer;

import com.cycling74.max.Atom;

public class Module extends ServerClient {

    public Module(Atom[] arguments) {
        this.declareIO(2, 1);
        this.check_arguments(arguments);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        System.out.print("OK!");
        ModuleServer module_server = ModuleServer.allocate(module_id);
        module_server.acquire_name(desired_name);
        this.attach_to_server(module_server);
        this.handle_response(this.server.generate_dumpmeta_response());
        this.generate_message_passer_callback();
    }

    @Override
    public void output_value_response_payload(Atom[] payload) {
        this.outlet(0, payload);
    }
}
