package oovu;

import oovu.clients.AttributeServerClient;
import oovu.servers.members.ReturnServer;

import com.cycling74.max.Atom;

public class Return extends AttributeServerClient {

    public Return(Atom[] arguments) {
        this.declareIO(2, 2);
        this.check_arguments(arguments);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.server = ReturnServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.server.server_clients.add(this);
        this.generate_message_passer_callback();
    }
}
