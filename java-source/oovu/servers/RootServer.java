package oovu.servers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.clients.ServerClient;
import oovu.events.KeyEvent;
import oovu.events.MouseEvent;
import oovu.eventscripts.EventManager;
import oovu.maxguis.MixerGui;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.Response;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;

public class RootServer extends Server {
    private EventManager event_manager = new EventManager();
    private MaxPatcher mixer_patcher = null;
    @SuppressWarnings("unused")
    private MaxPatcher input_patcher = null;

    public RootServer() {
        super();
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
        this.configure_events_goto_message_handler();
        this.configure_events_list_message_handler();
        this.configure_events_next_message_handler();
        this.configure_events_previous_message_handler();
        this.configure_events_read_message_handler();
        this.configure_key_message_handler();
        this.configure_mixer_closed_message_handler();
        this.configure_mixer_view_message_handler();
        this.configure_mouse_message_handler();
        this.configure_refresh_midi_message_handler();
        this.configure_state_message_handler();
        Environment.defer_low(new Executable() {
            @Override
            public void execute() {
                try {
                    MaxPatcher patcher = new MaxPatcher(0, 0, 100, 100);
                    patcher.newDefault(0, 0, "bpatcher",
                        Atom.parse("@name oovu.core.input"));
                    RootServer.this.input_patcher = patcher;
                } catch (UnsatisfiedLinkError e) {
                    Environment.log(e);
                }
            }
        });
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

    private void configure_events_goto_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("events/goto");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String event_name = null;
                if ((arguments.length == 1) && arguments[0].isInt()) {
                    event_name =
                        root_server.event_manager.get_event_name_by_index(arguments[0]
                            .toInt());
                } else if (0 < arguments.length) {
                    event_name =
                        root_server.event_manager.get_event_name_by_string(Atom
                            .toOneString(arguments));
                }
                if (event_name == null) {
                    return null;
                }
                State state =
                    root_server.event_manager.set_current_event_by_name(event_name);
                root_server.event_manager.execute_state(state, root_server);
                message_handler.client.handle_response(new Response(
                    message_handler.client, MaxIO.to_atoms("events/current", event_name),
                    null));
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_events_list_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("events/list");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                List<String> event_names = root_server.event_manager.get_event_names();
                if (event_names == null) {
                    return MaxIO.to_atoms(message_handler.get_name());
                }
                return MaxIO.to_atoms(message_handler.get_name(),
                    event_names.toArray(new String[0]));
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_events_next_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("events/next");
        builder.with_is_binding_relevant(true);
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String event_name = root_server.event_manager.get_next_event_name();
                if (event_name == null) {
                    return null;
                }
                State state =
                    root_server.event_manager.set_current_event_by_name(event_name);
                root_server.event_manager.execute_state(state, root_server);
                message_handler.client.handle_response(new Response(
                    message_handler.client, MaxIO.to_atoms("events/current", event_name),
                    null));
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_events_previous_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("events/previous");
        builder.with_is_binding_relevant(true);
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String event_name = root_server.event_manager.get_previous_event_name();
                if (event_name == null) {
                    return null;
                }
                State state =
                    root_server.event_manager.set_current_event_by_name(event_name);
                root_server.event_manager.execute_state(state, root_server);
                message_handler.client.handle_response(new Response(
                    message_handler.client, MaxIO.to_atoms("events/current", event_name),
                    null));
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_events_read_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("events/read");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.make_request(message_handler.client,
                    "events/list", null);
                return null;
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String filename = null;
                if (0 == arguments.length) {
                    filename = MaxSystem.openDialog();
                } else {
                    filename = Atom.toOneString(arguments);
                }
                if (filename != null) {
                    root_server.event_manager.parse_file(filename);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_key_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("key");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                if (arguments.length == 2) {
                    int ascii_number = arguments[0].toInt();
                    boolean depressed = arguments[1].toBoolean();
                    KeyEvent event = new KeyEvent(ascii_number, depressed);
                    Environment.event_service.publish(event);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_mixer_closed_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("mixer/closed");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                root_server.mixer_patcher = null;
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_mixer_view_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("mixer/view");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                if (root_server.mixer_patcher == null) {
                    root_server.mixer_patcher = MixerGui.build(root_server);
                }
                if (root_server.mixer_patcher != null) {
                    root_server.mixer_patcher.send("front", new Atom[0]);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_mouse_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("mouse");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                if (arguments.length == 6) {
                    boolean clicked = arguments[0].toBoolean();
                    int delta_clicked = arguments[1].toInt();
                    int x = arguments[2].toInt();
                    int y = arguments[3].toInt();
                    int delta_x = arguments[4].toInt();
                    int delta_y = arguments[5].toInt();
                    MouseEvent event =
                        new MouseEvent(clicked, delta_clicked, x, y, delta_x, delta_y);
                    Environment.event_service.publish(event);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_refresh_midi_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("refreshmidi");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                Environment.midi_event_service.update();
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_state_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("state");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                Atom[][] result = RootServer.this.get_formatted_state();
                for (int i = 0, j = result.length; i < j; i++) {
                    result[i] = Atom.newAtom(built_message_handler.get_name(), result[i]);
                }
                return result;
            }
        });
        this.add_message_handler(builder.build(this));
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
        StateComponentAggregate global_state = (StateComponentAggregate) this.get_state();
        commands.add(Atom.parse("wclose"));
        commands.add(Atom.parse("clear"));
        commands.add(Atom.parse("CUE NewCue"));
        commands.add(Atom.parse("cr"));
        for (State state : global_state.state_components) {
            if (!(state instanceof StateComponentAggregate)) {
                continue;
            }
            StateComponentAggregate module_state = (StateComponentAggregate) state;
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
        return new StateComponentAggregate(null, global_state.toArray(new State[0]));
    }
}
