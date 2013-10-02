package oovu;

import oovu.clients.LazyServerClient;
import oovu.servers.members.ReturnServer;

import com.cycling74.max.Atom;

public class LazyReturn extends LazyServerClient {

    public LazyReturn(Atom lazy_module_id, Atom lazy_name, Atom[] lazy_arguments) {
        super(lazy_module_id, lazy_name, lazy_arguments);
    }

    @Override
    public void bind(Atom[] arguments) {
        String desired_name = this.complete_lazy_name(arguments);
        Integer module_id = this.lazy_module_id.getInt();
        this.server = ReturnServer.allocate(module_id, desired_name,
            this.lazy_arguments);
        this.server.server_clients.add(this);
        this.handle_response(this.server.generate_dumpmeta_response());
        this.generate_message_passer_callback();
    }
}
