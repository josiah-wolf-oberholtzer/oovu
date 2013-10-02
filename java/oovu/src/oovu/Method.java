package oovu;

import oovu.clients.AttributeServerClient;
import oovu.servers.members.MethodServer;

import com.cycling74.max.Atom;

public class Method extends AttributeServerClient {

    public Method(Atom[] arguments) {
        this.declareIO(2, 2);
        this.check_arguments(arguments);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.server = MethodServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.server.server_clients.add(this);
        // this.handle_response(this.server.generate_dumpmeta_response());
        this.generate_message_passer_callback();
    }
}
