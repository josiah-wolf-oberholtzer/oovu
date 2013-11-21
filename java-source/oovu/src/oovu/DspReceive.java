package oovu;

import oovu.clients.ModuleMemberServerClient;
import oovu.servers.DspReceiveServer;

import com.cycling74.max.Atom;

public class DspReceive extends ModuleMemberServerClient {

    public DspReceive(Atom[] arguments) {
        super(arguments);
    }

    @Override
    public void bind(Atom[] arguments) {
        String desired_name = this.complete_lazy_name(arguments);
        this.server =
            DspReceiveServer.allocate(this.lazy_module_id, desired_name,
                this.lazy_arguments);
        this.server.server_clients.add(this);
        this.server.make_deferred_request(this, "dumpmeta", null);
    }
}
