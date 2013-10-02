package oovu;

import oovu.clients.AttributeServerClient;
import oovu.maxadapters.GenericMaxAdapter;
import oovu.servers.members.PropertyServer;

import com.cycling74.max.Atom;

public class Property extends AttributeServerClient {

    public Property(Atom[] arguments) {
        this.declareIO(2, 2);
        this.check_arguments(arguments);
        this.max_adapter = new GenericMaxAdapter(this);
        Integer module_id = arguments[0].toInt();
        String desired_name = arguments[1].toString();
        this.server = PropertyServer.allocate(module_id, desired_name,
            Atom.removeFirst(arguments, 2));
        this.server.server_clients.add(this);
        this.generate_message_passer_callback();
    }
}
