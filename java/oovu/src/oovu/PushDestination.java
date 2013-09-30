package oovu;

import oovu.clients.AudioServerClient;
import oovu.servers.members.PushServer;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

public class PushDestination extends AudioServerClient {

    public PushDestination(Atom[] arguments) {
        if (arguments.length < 2) {
            MaxObject.bail("Bad arguments.");
        } else if (!arguments[0].isInt()) {
            MaxObject.bail("Bad arguments.");
        }
        this.declareIO(2, 1);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.server = PushServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.server.server_clients.add(this);
        this.generate_message_passer_callback();
    }
}
