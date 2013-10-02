package oovu;

import oovu.clients.LazyServerClient;
import oovu.maxadapters.GenericMaxAdapter;
import oovu.servers.members.ReturnServer;

import com.cycling74.max.Atom;

public class LazyReturn extends LazyServerClient {

    public LazyReturn(Atom[] arguments) {
        super(arguments);
        this.max_adapter = new GenericMaxAdapter(this);
    }

    @Override
    public void bind(Atom[] arguments) {
        String desired_name = this.complete_lazy_name(arguments);
        this.server = ReturnServer.allocate(this.lazy_module_id, desired_name,
            this.lazy_arguments);
        this.server.server_clients.add(this);
        this.generate_message_passer_callback();
    }
}
