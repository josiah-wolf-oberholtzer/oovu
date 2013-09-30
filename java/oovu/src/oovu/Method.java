package oovu;

import oovu.clients.AttributeServerClient;
import oovu.servers.members.MethodServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class Method extends AttributeServerClient {

    public Method(Atom[] arguments) {
        if (arguments.length < 2) {
            MaxObject.bail("Bad arguments.");
        } else if (!arguments[0].isInt()) {
            MaxObject.bail("Bad arguments.");
        }
        this.declareIO(2, 2);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.server = MethodServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.server.server_clients.add(this);
        this.handle_response(this.server.generate_dumpmeta_response());
        this.generate_server_client_creation_callback();
    }
}
