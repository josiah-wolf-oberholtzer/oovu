package oovu.servers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.clients.ServerClient;
import oovu.eventscripts.EventManager;
import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.Response;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;

public class RootServer extends Server {
    private EventManager event_manager = new EventManager();

    public RootServer() {
        super();
        this.attach_to_osc_address_node(Environment.root_osc_address_node);
        // MIXER
        MessageHandlerBuilder mixer_builder =
            new MessageHandlerBuilder("mixer");
        mixer_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                RootServer server = (RootServer) message_handler.client;
                MaxPatcher mixer_patcher = server.build_mixer_patcher();
                mixer_patcher.send("front", new Atom[0]);
                return null;
            }
        });
        this.add_message_handler(mixer_builder.build(this));
        // STATE
        MessageHandlerBuilder state_builder =
            new MessageHandlerBuilder("state");
        state_builder.with_getter(new MessageHandlerCallback() {
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
        });
        this.add_message_handler(state_builder.build(this));
        // EVENTS/LIST
        MessageHandlerBuilder events_list_builder =
            new MessageHandlerBuilder("events/list");
        events_list_builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                List<String> event_names =
                    root_server.event_manager.get_event_names();
                if (event_names == null) {
                    return Atoms.to_atoms(message_handler.get_name());
                }
                return Atoms.to_atoms(message_handler.get_name(),
                    event_names.toArray(new String[0]));
            }
        });
        this.add_message_handler(events_list_builder.build(this));
        // EVENTS/NEXT
        MessageHandlerBuilder events_next_builder =
            new MessageHandlerBuilder("events/next");
        events_next_builder.with_is_binding_relevant(true);
        events_next_builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String event_name =
                    root_server.event_manager.get_next_event_name();
                if (event_name == null) {
                    return null;
                }
                State state =
                    root_server.event_manager
                        .set_current_event_by_name(event_name);
                root_server.event_manager.execute_state(state, root_server);
                message_handler.client.handle_response(new Response(
                    message_handler.client, Atoms.to_atoms("events/current",
                        event_name), null));
                return null;
            }
        });
        this.add_message_handler(events_next_builder.build(this));
        // EVENTS/PREVIOUS
        MessageHandlerBuilder events_previous_builder =
            new MessageHandlerBuilder("events/previous");
        events_previous_builder.with_is_binding_relevant(true);
        events_previous_builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String event_name =
                    root_server.event_manager.get_previous_event_name();
                if (event_name == null) {
                    return null;
                }
                State state =
                    root_server.event_manager
                        .set_current_event_by_name(event_name);
                root_server.event_manager.execute_state(state, root_server);
                message_handler.client.handle_response(new Response(
                    message_handler.client, Atoms.to_atoms("events/current",
                        event_name), null));
                return null;
            }
        });
        this.add_message_handler(events_previous_builder.build(this));
        // EVENTS/GOTO
        MessageHandlerBuilder events_goto_builder =
            new MessageHandlerBuilder("events/goto");
        events_goto_builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                RootServer root_server = (RootServer) message_handler.client;
                String event_name = null;
                if ((arguments.length == 1) && arguments[0].isInt()) {
                    event_name =
                        root_server.event_manager
                            .get_event_name_by_index(arguments[0].toInt());
                } else if (0 < arguments.length) {
                    event_name =
                        root_server.event_manager.get_event_name_by_string(Atom
                            .toOneString(arguments));
                }
                if (event_name == null) {
                    return null;
                }
                State state =
                    root_server.event_manager
                        .set_current_event_by_name(event_name);
                root_server.event_manager.execute_state(state, root_server);
                message_handler.client.handle_response(new Response(
                    message_handler.client, Atoms.to_atoms("events/current",
                        event_name), null));
                return null;
            }
        });
        this.add_message_handler(events_goto_builder.build(this));
        // EVENTS/READ
        MessageHandlerBuilder events_read_builder =
            new MessageHandlerBuilder("events/read");
        events_read_builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                message_handler.client.make_request(message_handler.client,
                    "events/list", null);
                return null;
            }
        });
        events_read_builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
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
        this.add_message_handler(events_read_builder.build(this));
    }

    public MaxPatcher build_mixer_patcher() {
        ArrayList<ModuleServer> effects_modules = new ArrayList<ModuleServer>();
        ArrayList<ModuleServer> input_only_modules =
            new ArrayList<ModuleServer>();
        ArrayList<ModuleServer> output_only_modules =
            new ArrayList<ModuleServer>();
        for (Server child_server : this.child_servers) {
            ModuleServer module = (ModuleServer) child_server;
            DspSettingsServer dsp_settings = module.get_dsp_settings_server();
            boolean has_receives = dsp_settings.module_has_dsp_receives();
            boolean has_sends = dsp_settings.module_has_dsp_sends();
            if ((!has_receives) && (!has_sends)) {
                continue;
            } else if (has_receives && has_sends) {
                effects_modules.add(module);
            } else if (has_receives) {
                input_only_modules.add(module);
            } else {
                output_only_modules.add(module);
            }
        }
        int gutter = 10;
        int step = 145;
        int width = 70;
        int sections = 0;
        if (0 < input_only_modules.size()) {
            width += (step * input_only_modules.size());
            sections += 1;
        }
        if (0 < output_only_modules.size()) {
            width += (step * output_only_modules.size());
            sections += 1;
        }
        if (0 < effects_modules.size()) {
            width += (step * effects_modules.size());
            sections += 1;
        }
        if (sections == 0) {
            return null;
        } else if (sections == 2) {
            width += gutter;
        } else {
            width += gutter * 2;
        }
        MaxPatcher patcher = new MaxPatcher(0, 0, width, 745);
        int current_x = 70;
        patcher.newDefault(5, 5, "bpatcher",
            Atom.parse("@patching_rect 5 5 50 70 @name oovu.mixer.globals"));
        patcher.newDefault(60, 735, "live.line",
            Atom.parse("@patching_rect 60 5 5 735 @border 2 @justification 1"));
        if (0 < input_only_modules.size()) {
            patcher.newDefault(
                current_x,
                5,
                "comment",
                Atom.parse("@text INPUTS @textcolor 1 1 1 1 @fontface 3 "
                    + "@patching_rect " + current_x + " 5 140 20"));
            for (ModuleServer module : output_only_modules) {
                module.fill_mixer_patcher(patcher, current_x, 30);
                current_x += step;
            }
            if ((0 < effects_modules.size())
                || (0 < output_only_modules.size())) {
                patcher.newDefault(
                    current_x,
                    735,
                    "live.line",
                    Atom.parse("@patching_rect " + current_x
                        + " 5 5 735 @border 2 @justification 1"));
                current_x += gutter;
            }
        }
        if (0 < effects_modules.size()) {
            patcher.newDefault(
                current_x,
                5,
                "comment",
                Atom.parse("@text TREATMENTS @textcolor 1 1 1 1 @fontface 3 "
                    + "@patching_rect " + current_x + " 5 140 20"));
            for (ModuleServer module : effects_modules) {
                module.fill_mixer_patcher(patcher, current_x, 30);
                current_x += step;
            }
            if (0 < output_only_modules.size()) {
                patcher.newDefault(
                    current_x,
                    735,
                    "live.line",
                    Atom.parse("@patching_rect " + current_x
                        + " 5 5 735 @border 2 @justification 1"));
                current_x += gutter;
            }
        }
        if (0 < output_only_modules.size()) {
            patcher.newDefault(
                current_x,
                5,
                "comment",
                Atom.parse("@text OUTPUTS @textcolor 1 1 1 1 @fontface 3 "
                    + "@patching_rect " + current_x + " 5 140 20"));
            for (ModuleServer module : input_only_modules) {
                module.fill_mixer_patcher(patcher, current_x, 30);
                current_x += step;
            }
        }
        patcher.setBackgroundColor(0, 0, 0);
        patcher.send("statusbarvisible", Atom.parse("0"));
        patcher.send("toolbarvisible", Atom.parse("0"));
        patcher.getWindow().setTitle("OOVU Mixer");
        return patcher;
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
