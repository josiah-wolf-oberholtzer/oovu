package oovu;

import oovu.clients.AudioServerClient;
import oovu.servers.members.PushServer;

import com.cycling74.max.Atom;

public class PushDestination extends AudioServerClient {

    public PushDestination(Atom[] arguments) {
        this.declareIO(2, 1);
        this.check_arguments(arguments);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.server = PushServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.server.server_clients.add(this);
        this.generate_message_passer_callback();
    }
}
