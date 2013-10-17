package oovu.servers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.clients.ServerClient;
import oovu.messaging.MessageHandler;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

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
            Atom[][] result = RootServer.this.get_formatted_state();
            for (int i = 0, j = result.length; i < j; i++) {
                result[i] = Atom.newAtom("state", result[i]);
            }
            return result;
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

    public List<ModuleServer> get_child_module_servers() {
        ArrayList<ModuleServer> module_servers = new ArrayList<ModuleServer>();
        for (Server child : this.child_servers) {
            if (child instanceof ModuleServer) {
                module_servers.add((ModuleServer) child);
            }
        }
        Collections.sort(module_servers);
        return module_servers;
    }

    public Atom[][] get_formatted_state() {
        ArrayList<Atom[]> commands = new ArrayList<Atom[]>();
        StateComponentAggregate global_state =
            (StateComponentAggregate) this.get_state();
        commands.add(Atom.parse("wclose"));
        commands.add(Atom.parse("clear"));
        commands.add(Atom.parse("CUE NewCue"));
        commands.add(Atom.parse("cr"));
        for (State state : global_state.state_components) {
            if (!(state instanceof StateComponentAggregate)) {
                continue;
            }
            StateComponentAggregate module_state =
                (StateComponentAggregate) state;
            commands.add(Atom.parse("cr"));
            commands.add(Atom.parse("tab"));
            commands.add(Atom.newAtom(new String[] {
                "###", module_state.name, "###"
            }));
            commands.add(Atom.parse("cr"));
            commands.add(Atom.parse("cr"));
            for (Atom[] atoms : module_state.toAtoms()) {
                commands.add(Atom.parse("tab"));
                commands.add(atoms);
                commands.add(Atom.parse("cr"));
            }
        }
        commands.add(Atom.parse("open"));
        return commands.toArray(new Atom[0][]);
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
    public State get_state() {
        ArrayList<State> global_state = new ArrayList<State>();
        for (Server module_server : this.get_child_module_servers()) {
            global_state.add(module_server.get_state());
        }
        return new StateComponentAggregate(null,
            global_state.toArray(new State[0]));
    }
}
