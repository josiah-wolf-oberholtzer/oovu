package oovu;

import oovu.clients.ModuleMemberServerClient;
import oovu.servers.DspSendServer;

import com.cycling74.max.Atom;

public class DspSend extends ModuleMemberServerClient {
    public DspSend(Atom[] arguments) {
        super(arguments);
    }

    @Override
    public void bind(Atom[] arguments) {
        String desired_name = this.complete_lazy_name(arguments);
        if (desired_name != null) {
            this.server =
                DspSendServer.allocate(this.lazy_module_id, desired_name,
                    this.lazy_arguments);
            this.server.server_clients.add(this);
            this.server.make_deferred_request(this, "dumpmeta", null);
        }
    }
}
