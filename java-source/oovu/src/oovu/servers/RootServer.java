package oovu.servers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.clients.ServerClient;
import oovu.eventscripts.EventScriptParser;
import oovu.messaging.ActionMessageHandler;
import oovu.messaging.MessageHandler;
import oovu.messaging.Request;
import oovu.messaging.SetterMessageHandler;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

public class RootServer extends Server {
    private class GetStateMessageHandler extends MessageHandler {
        public GetStateMessageHandler(Server client) {
            super(client, "getstate");
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_binding_relevant() {
            return false;
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
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

    private class ListEventsMessageHandler extends MessageHandler {
        public ListEventsMessageHandler(Server client) {
            super(client, "events/list");
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_binding_relevant() {
            return false;
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            if (RootServer.this.events != null) {
                Set<Entry<String, State>> entry_set =
                    RootServer.this.events.entrySet();
                int counter = 0;
                result[0] = new Atom[entry_set.size()];
                for (Entry<String, State> entry : entry_set) {
                    result[0][counter] = Atom.newAtom(entry.getKey());
                    counter += 1;
                }
            } else {
                result[0] = new Atom[0];
            }
            result[0] = Atom.newAtom("events", result[0]);
            return result;
        }
    }

    private class NextEventMessageHandler extends ActionMessageHandler {
        public NextEventMessageHandler(Server client) {
            super(client, "events/next");
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (RootServer.this.events == null) {
                return null;
            } else if (RootServer.this.events.size() == 0) {
                return null;
            }
            String[] event_names =
                RootServer.this.events.keySet().toArray(new String[0]);
            int event_index =
                Arrays.asList(event_names).indexOf(
                    RootServer.this.current_event_name);
            if (event_index == -1) {
                event_index = 0;
            } else {
                event_index += 1;
            }
            if (event_index == event_names.length) {
                event_index -= 1;
            }
            MessageHandler message_handler =
                RootServer.this.message_handlers.get("events/goto");
            Atom[] message_arguments = Atom.newAtom(new int[] {
                event_index
            });
            message_handler.run(message_arguments);
            return null;
        }
    }

    private class PreviousEventMessageHandler extends ActionMessageHandler {
        public PreviousEventMessageHandler(Server client) {
            super(client, "events/previous");
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (RootServer.this.events == null) {
                return null;
            } else if (RootServer.this.events.size() == 0) {
                return null;
            }
            String[] event_names =
                RootServer.this.events.keySet().toArray(new String[0]);
            int event_index =
                Arrays.asList(event_names).indexOf(
                    RootServer.this.current_event_name);
            event_index -= 1;
            if (event_index < 0) {
                event_index = 0;
            }
            MessageHandler message_handler =
                RootServer.this.message_handlers.get("events/goto");
            Atom[] message_arguments = Atom.newAtom(new int[] {
                event_index
            });
            message_handler.run(message_arguments);
            return null;
        }
    }

    private class ReadEventScriptMessageHandler extends MessageHandler {
        public ReadEventScriptMessageHandler(Server client) {
            super(client, "events/read");
        }

        @Override
        public void call_after() {
            Request request =
                new Request(RootServer.this,
                    OscAddress.from_cache("./:events/list"), new Atom[0], false);
            RootServer.this.handle_request(request);
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_binding_relevant() {
            return false;
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String filename = null;
            if (0 == arguments.length) {
                filename = MaxSystem.openDialog();
            } else {
                filename = Atom.toOneString(arguments);
            }
            if (filename != null) {
                RootServer.this.events =
                    new EventScriptParser().parse_file(filename);
            }
            return null;
        }
    }

    private class SetEventMessageHandler extends SetterMessageHandler {
        public SetEventMessageHandler(Server client) {
            super(client, "events/goto");
        }

        @Override
        public Integer get_arity() {
            return null;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (RootServer.this.events == null) {
                return null;
            } else if (0 == arguments.length) {
                return null;
            }
            String cue_name = null;
            if ((arguments.length == 1) && arguments[0].isInt()) {
                String[] cue_names =
                    RootServer.this.events.keySet().toArray(new String[0]);
                cue_name = cue_names[arguments[0].toInt()];
            } else {
                cue_name = Atom.toOneString(arguments);
            }
            StateComponentAggregate cue =
                (StateComponentAggregate) RootServer.this.events.get(cue_name);
            if (cue == null) {
                return null;
            }
            for (Atom[] atoms : cue.toAtoms()) {
                String cue_address_string = atoms[0].getString();
                OscAddress cue_address =
                    OscAddress.from_cache(cue_address_string);
                Atom[] cue_arguments = Atom.removeFirst(atoms);
                Request request =
                    new Request(RootServer.this, cue_address, cue_arguments,
                        false);
                Set<OscAddressNode> osc_address_nodes =
                    RootServer.this.get_osc_address_node().search(
                        request.destination);
                for (OscAddressNode osc_address_node : osc_address_nodes) {
                    Server server = osc_address_node.get_server();
                    if (server != null) {
                        server.handle_request(request);
                    }
                }
            }
            return null;
        }
    }

    private Map<String, State> events = null;
    private String current_event_name = null;

    public RootServer() {
        super();
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
        this.add_message_handler(new ListEventsMessageHandler(this));
        this.add_message_handler(new GetStateMessageHandler(this));
        this.add_message_handler(new ReadEventScriptMessageHandler(this));
        this.add_message_handler(new SetEventMessageHandler(this));
        this.add_message_handler(new NextEventMessageHandler(this));
        this.add_message_handler(new PreviousEventMessageHandler(this));
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
