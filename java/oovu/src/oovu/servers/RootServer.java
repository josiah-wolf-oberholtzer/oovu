package oovu.servers;

import java.util.ArrayList;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.clients.ServerClient;
import oovu.messaging.MessageHandler;
import oovu.messaging.Request;

import com.cycling74.max.Atom;

public class RootServer extends Server {

    private class GetStateMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getstate";
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] state = RootServer.this.get_state();
            for (int i = 0, j = state.length; i < j; i++) {
                state[i] = Atom.newAtom("state", state[i]);
            }
            return state;
        }
    }

    public RootServer() {
        super(null);
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
        this.add_message_handler(new GetStateMessageHandler());
    }

    @Override
    public void clear() {
        for (Server child_server : this.child_servers.toArray(new Server[0])) {
            child_server.detach_from_parent_server();
        }
        this.detach_from_parent_server();
        for (ServerClient server_client : this.server_clients) {
            server_client.detach_from_server();
        }
    }

    @Override
    public OscAddress get_osc_address() {
        return OscAddress.from_cache(this.get_osc_address_string());
    }

    @Override
    public String get_osc_address_string() {
        return "/";
    }

    @Override
    public Atom[][] get_state() {
        ArrayList<Atom[]> state = new ArrayList<Atom[]>();
        for (Server module_server : this.child_servers) {
            for (Atom[] module_state : module_server.get_state()) {
                state.add(module_state);
            }
        }
        return state.toArray(new Atom[0][]);
    }

    @Override
    public void handle_request(Request request) {
    }
}
