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
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.Request;
import oovu.messaging.Setter;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxSystem;

public class RootServer extends Server {
    private Map<String, State> events = null;
    private String current_event_name = null;

    public RootServer() {
        super();
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
        // this.add_message_handler(new GetStateMessageHandler(this));
        // this.add_message_handler(new ListEventsMessageHandler(this));
        // this.add_message_handler(new NextEventMessageHandler(this));
        // this.add_message_handler(new PreviousEventMessageHandler(this));
        // this.add_message_handler(new ReadEventScriptMessageHandler(this));
        // this.add_message_handler(new SetEventMessageHandler(this));
        this.add_message_handler(new MessageHandlerBuilder("state")
            .with_getter(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    Atom[][] result = RootServer.this.get_formatted_state();
                    for (int i = 0, j = result.length; i < j; i++) {
                        result[i] =
                            Atom.newAtom(built_message_handler.get_name(),
                                result[i]);
                    }
                    return result;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("events/list")
            .with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    RootServer root_server =
                        (RootServer) built_message_handler.client;
                    Atom[][] result = new Atom[1][];
                    if (root_server.events != null) {
                        Set<Entry<String, State>> entry_set =
                            root_server.events.entrySet();
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
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("events/next")
            .with_is_binding_relevant(true).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    RootServer root_server =
                        (RootServer) built_message_handler.client;
                    if (root_server.events == null) {
                        return null;
                    } else if (root_server.events.size() == 0) {
                        return null;
                    }
                    String[] event_names =
                        root_server.events.keySet().toArray(new String[0]);
                    int event_index =
                        Arrays.asList(event_names).indexOf(
                            root_server.current_event_name);
                    if (event_index == -1) {
                        event_index = 0;
                    } else {
                        event_index += 1;
                    }
                    if (event_index == event_names.length) {
                        event_index -= 1;
                    }
                    Atom[] message_arguments = Atom.newAtom(new int[] {
                        event_index
                    });
                    root_server.make_request(root_server, "events/goto",
                        message_arguments);
                    return null;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("events/previous")
            .with_is_binding_relevant(true).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    RootServer root_server =
                        (RootServer) built_message_handler.client;
                    if (root_server.events == null) {
                        return null;
                    } else if (root_server.events.size() == 0) {
                        return null;
                    }
                    String[] event_names =
                        root_server.events.keySet().toArray(new String[0]);
                    int event_index =
                        Arrays.asList(event_names).indexOf(
                            root_server.current_event_name);
                    event_index -= 1;
                    if (event_index < 0) {
                        event_index = 0;
                    }
                    Atom[] message_arguments = Atom.newAtom(new int[] {
                        event_index
                    });
                    root_server.make_request(root_server, "events/goto",
                        message_arguments);
                    return null;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("events/read")
            .with_callback(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    built_message_handler.client.make_request(
                        built_message_handler.client, "events/list", null);
                    return null;
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    RootServer root_server =
                        (RootServer) built_message_handler.client;
                    String filename = null;
                    if (0 == arguments.length) {
                        filename = MaxSystem.openDialog();
                    } else {
                        filename = Atom.toOneString(arguments);
                    }
                    if (filename != null) {
                        root_server.events =
                            new EventScriptParser().parse_file(filename);
                    }
                    return null;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("events/goto")
            .with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    RootServer root_server =
                        (RootServer) built_message_handler.client;
                    if (root_server.events == null) {
                        return null;
                    } else if (0 == arguments.length) {
                        return null;
                    }
                    String cue_name = null;
                    if ((arguments.length == 1) && arguments[0].isInt()) {
                        String[] cue_names =
                            root_server.events.keySet().toArray(new String[0]);
                        cue_name = cue_names[arguments[0].toInt()];
                    } else {
                        cue_name = Atom.toOneString(arguments);
                    }
                    StateComponentAggregate cue =
                        (StateComponentAggregate) root_server.events
                            .get(cue_name);
                    if (cue == null) {
                        return null;
                    }
                    for (Atom[] atoms : cue.toAtoms()) {
                        String cue_address_string = atoms[0].getString();
                        OscAddress cue_address =
                            OscAddress.from_cache(cue_address_string);
                        Atom[] cue_arguments = Atom.removeFirst(atoms);
                        Request request =
                            new Request(root_server, cue_address,
                                cue_arguments, false);
                        Set<OscAddressNode> osc_address_nodes =
                            root_server.get_osc_address_node().search(
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
            }).build(this));
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
