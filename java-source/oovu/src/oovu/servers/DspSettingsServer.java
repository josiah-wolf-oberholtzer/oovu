package oovu.servers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.events.types.DspSettingsChangedEvent;
import oovu.messaging.Atoms;
import oovu.messaging.MessageHandler;
import oovu.messaging.Getter;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.Setter;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSettingsServer extends ModuleMemberServer {
    private boolean is_active = false;
    private Integer input_count = null;
    private Integer output_count = null;
    private Integer send_count = 1;
    private Integer voice_count = 1;
    private boolean limiting = true;

    public DspSettingsServer(ModuleServer module_server) {
        super(module_server);
        this.add_message_handler(new MessageHandlerBuilder("active")
            .with_callback(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    built_message_handler.client.make_request(
                        built_message_handler.client,
                        built_message_handler.get_getter_name(), null);
                    return null;
                }
            }).with_is_meta_relevant(true).with_is_state_relevant(true)
            .with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    return Atoms.to_atoms(
                        built_message_handler.get_setter_name(),
                        server.get_is_active());
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    if (0 < arguments.length) {
                        boolean argument = arguments[0].toBoolean();
                        server.set_is_active(argument);
                    }
                    return null;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("inputcount")
            .with_is_meta_relevant(true).with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    return Atoms.to_atoms(
                        built_message_handler.get_setter_name(),
                        server.get_input_count());
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("limiting")
            .with_callback(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    built_message_handler.client.make_request(
                        built_message_handler.client,
                        built_message_handler.get_getter_name(), null);
                    return null;
                }
            }).with_is_meta_relevant(true).with_is_state_relevant(true)
            .with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    return Atoms.to_atoms(
                        built_message_handler.get_setter_name(),
                        server.get_limiting());
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    if (0 < arguments.length) {
                        boolean argument = arguments[0].toBoolean();
                        server.set_limiting(argument);
                    }
                    return null;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("outputcount")
            .with_is_meta_relevant(true).with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    return Atoms.to_atoms(
                        built_message_handler.get_setter_name(),
                        server.get_output_count());
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("sendcount")
            .with_callback(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    built_message_handler.client.make_request(
                        built_message_handler.client,
                        built_message_handler.get_getter_name(), null);
                    return null;
                }
            }).with_is_meta_relevant(true).with_is_state_relevant(true)
            .with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    return Atoms.to_atoms(
                        built_message_handler.get_setter_name(),
                        server.get_send_count());
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    if (0 < arguments.length) {
                        int argument = arguments[0].toInt();
                        server.set_send_count(argument);
                    }
                    return null;
                }
            }).build(this));
        this.add_message_handler(new MessageHandlerBuilder("voicecount")
            .with_callback(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    built_message_handler.client.make_request(
                        built_message_handler.client, "getvoicecount", null);
                    built_message_handler.client.make_request(
                        built_message_handler.client, "getinputcount", null);
                    built_message_handler.client.make_request(
                        built_message_handler.client, "getoutputcount", null);
                    Environment.event_service
                        .publish(new DspSettingsChangedEvent(
                            DspSettingsServer.this));
                    return null;
                }
            }).with_is_meta_relevant(true).with_is_state_relevant(true)
            .with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    return Atoms.to_atoms(
                        built_message_handler.get_setter_name(),
                        server.get_voice_count());
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    DspSettingsServer server =
                        (DspSettingsServer) built_message_handler.client;
                    if (0 < arguments.length) {
                        int argument = arguments[0].toInt();
                        server.set_voice_count(argument);
                    }
                    return null;
                }
            }).build(this));
    }

    public void configure(Atom[] arguments) {
        if (this.is_configured) {
            return;
        }
        Map<String, Atom[]> argument_map = Atoms.to_map(arguments);
        List<Integer> valid_counts =
            new ArrayList<Integer>(Arrays.asList(0, 1, 2, 4, 8));
        if (argument_map.containsKey("inputs")) {
            int input_count = argument_map.get("inputs")[0].getInt();
            if (input_count < 0) {
                input_count = 0;
            } else if (8 < input_count) {
                input_count = 8;
            }
            if (valid_counts.contains(input_count)) {
                this.input_count = input_count;
            }
        }
        if (argument_map.containsKey("outputs")) {
            int output_count = argument_map.get("outputs")[0].getInt();
            if (output_count < 0) {
                output_count = 0;
            } else if (8 < output_count) {
                output_count = 8;
            }
            if (valid_counts.contains(output_count)) {
                this.output_count = output_count;
            }
        }
        this.is_configured = true;
        Environment.event_service.publish(new DspSettingsChangedEvent(this));
    }

    public int get_input_count() {
        if (this.input_count_is_static()) {
            return this.input_count;
        }
        return this.voice_count;
    }

    public boolean get_is_active() {
        return this.is_active;
    }

    public boolean get_limiting() {
        return this.limiting;
    }

    public int get_output_count() {
        if (this.output_count_is_static()) {
            return this.output_count;
        }
        return this.voice_count;
    }

    public int get_send_count() {
        return this.send_count;
    }

    @Override
    public State get_state() {
        return null;
    }

    public int get_voice_count() {
        if (this.input_count_is_static()) {
            if (0 == this.get_input_count()) {
                return 1;
            }
            return this.input_count;
        }
        return this.voice_count;
    }

    public boolean input_count_is_static() {
        if (this.input_count != null) {
            return true;
        }
        return false;
    }

    public boolean module_has_dsp_receives() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        for (Server server : module_server.child_servers) {
            if (server instanceof DspReceiveServer) {
                return true;
            }
        }
        return false;
    }

    public boolean module_has_dsp_sends() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        for (Server server : module_server.child_servers) {
            if (server instanceof DspSendServer) {
                return true;
            }
        }
        return false;
    }

    public boolean output_count_is_static() {
        if (this.output_count != null) {
            return true;
        }
        return false;
    }

    public void set_is_active(boolean is_active) {
        this.is_active = is_active;
    }

    public void set_limiting(boolean limiting) {
        this.limiting = limiting;
    }

    public void set_send_count(int send_count) {
        if ((0 < send_count) && (send_count <= 8)) {
            this.send_count = send_count;
        }
    }

    public void set_voice_count(int voice_count) {
        List<Integer> valid_counts =
            new ArrayList<Integer>(Arrays.asList(1, 2, 4, 8));
        if ((0 < voice_count) && (voice_count <= 8)) {
            if (!this.input_count_is_static()) {
                if (valid_counts.contains(voice_count)) {
                    this.voice_count = voice_count;
                }
            }
        }
    }
}
