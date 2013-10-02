package oovu;

import oovu.clients.LazyServerClient;
import oovu.servers.members.PropertyServer;

import com.cycling74.max.Atom;

public class LazyProperty extends LazyServerClient {

    public LazyProperty(Atom[] arguments) {
        super(arguments);
    }

    @Override
    public void bind(Atom[] arguments) {
        String desired_name = this.complete_lazy_name(arguments);
        this.server = PropertyServer.allocate(this.lazy_module_id,
            desired_name, this.lazy_arguments);
        this.server.server_clients.add(this);
        this.generate_message_passer_callback();
    }
}
