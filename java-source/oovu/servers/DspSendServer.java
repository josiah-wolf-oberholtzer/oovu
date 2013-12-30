package oovu.servers;

import java.util.ArrayList;

import oovu.addresses.OscAddress;
import oovu.datatypes.AudioSendDatatype;
import oovu.events.DspSettingsChangedEvent;
import oovu.events.Event;
import oovu.events.PublisherFilter;
import oovu.events.Subscription;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSendServer extends ModuleMemberServer {
    private class DspSettingsChangedSubscription extends Subscription {
        public DspSettingsChangedSubscription(Server subscriber, Server publisher) {
            super(subscriber, DspSettingsChangedEvent.class, new PublisherFilter(
                publisher));
        }

        @Override
        public void handle_event(Event event) {
            this.subscriber.make_request(this.subscriber, "dumpmeta", null);
        }
    }

    private class Routing {
        public final int input;
        public final int output;
        public final double gain;

        public Routing(int input, int output, double gain) {
            this.input = input;
            this.output = output;
            this.gain = gain;
        }

        public Atom[] to_atoms() {
            Atom[] result =
                new Atom[] {
                    Atom.newAtom(this.input),
                    Atom.newAtom(this.output),
                    Atom.newAtom(this.gain),
                };
            return result;
        }
    }

    public static DspSendServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (DspSendServer) ModuleMemberServer.allocate_from_label("DspSendServer",
            module_id, desired_name);
    }

    private DspReceiveServer destination_server;
    private Subscription source_subscription;
    private Subscription target_subscription;

    public DspSendServer(ModuleServer module_server) {
        super(module_server);
        this.destination_server = null;
        this.source_subscription =
            new DspSettingsChangedSubscription(this,
                module_server.get_dsp_settings_server());
        this.source_subscription.subscribe();
        this.configure_destination_message_handler();
        this.configure_destinationid_message_handler();
        this.configure_destinations_message_handler();
        this.configure_io_message_handler();
        this.configure_routing_message_handler();
    }

    private void configure_destination_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("destination");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                built_message_handler.client.make_request(built_message_handler.client,
                    "dumpmeta", null);
                return null;
            }
        });
        builder.with_is_meta_relevant(true);
        builder.with_is_state_relevant(true);
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                DspSendServer server = (DspSendServer) built_message_handler.client;
                return MaxIO.to_atoms(built_message_handler.get_name(),
                    server.get_destination_address_string());
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                DspSendServer server = (DspSendServer) built_message_handler.client;
                if ((0 == arguments.length)
                    || (arguments[0].isString() && arguments[0].getString().equals("---"))) {
                    server.set_destination_server(null);
                } else {
                    String address_string = arguments[0].getString();
                    OscAddress destination_address =
                        OscAddress.from_cache(address_string);
                    DspReceiveServer destination_server =
                        DspReceiveServer.dsp_receive_servers.get(destination_address);
                    server.set_destination_server(destination_server);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_destinationid_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("destinationid");
        builder.with_is_meta_relevant(true);
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                DspSendServer server = (DspSendServer) built_message_handler.client;
                return MaxIO.to_atoms(built_message_handler.get_name(),
                    server.get_destination_id());
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_destinations_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("destinations");
        builder.with_is_meta_relevant(true);
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                return MaxIO.to_atoms("destinations",
                    AudioSendDatatype.get_destinations());
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_io_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("io");
        builder.with_is_meta_relevant(true);
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                DspSendServer server = (DspSendServer) built_message_handler.client;
                Atom[][] result = new Atom[1][];
                int[] io = server.get_io();
                result[0] = Atom.newAtom("io", Atom.newAtom(io));
                return result;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_routing_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("routing");
        builder.with_is_meta_relevant(true);
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                DspSendServer server = (DspSendServer) built_message_handler.client;
                Routing[] routing = server.get_routing();
                if (0 == routing.length) {
                    return MaxIO.to_atoms("routing", "clear");
                }
                ArrayList<Atom[]> result = new ArrayList<Atom[]>();
                result.add(Atom.newAtom(new String[] {
                    "routing", "clear"
                }));
                for (Routing element : routing) {
                    Atom[] atoms = element.to_atoms();
                    result.add(Atom.newAtom("routing", atoms));
                }
                return result.toArray(new Atom[0][]);
            }
        });
        this.add_message_handler(builder.build(this));
    }

    public String get_destination_address_string() {
        if (this.destination_server != null) {
            return this.destination_server.get_osc_address_string();
        }
        return null;
    }

    public Integer get_destination_id() {
        DspReceiveServer destination_server = this.get_destination_server();
        if (destination_server == null) {
            return System.identityHashCode(this);
        }
        return System.identityHashCode(destination_server);
    }

    public DspReceiveServer get_destination_server() {
        return this.destination_server;
    }

    public int[] get_io() {
        int[] io = new int[] {
            0, 0
        };
        io[0] = this.get_output_count();
        if (this.destination_server != null) {
            io[1] = this.destination_server.get_input_count();
        }
        return io;
    }

    public int get_output_count() {
        ModuleServer module_server = (ModuleServer) this.parent_server;
        if (module_server == null) {
            return 0;
        }
        DspSettingsServer dsp_settings_server = module_server.get_dsp_settings_server();
        if (dsp_settings_server == null) {
            return 1;
        }
        return dsp_settings_server.get_output_count();
    }

    public Routing[] get_routing() {
        int[] io = this.get_io();
        switch (io[0]) {
            case 1:
                switch (io[1]) {
                    case 1:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                        };
                    case 2:
                        return new Routing[] {
                            new Routing(0, 0, 1.), new Routing(0, 1, 1.),
                        };
                    case 4:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(0, 1, 1.),
                            new Routing(0, 2, 1.),
                            new Routing(0, 3, 1.),
                        };
                    case 8:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(0, 1, 1.),
                            new Routing(0, 2, 1.),
                            new Routing(0, 3, 1.),
                            new Routing(0, 4, 1.),
                            new Routing(0, 5, 1.),
                            new Routing(0, 6, 1.),
                            new Routing(0, 7, 1.),
                        };
                }
            case 2:
                switch (io[1]) {
                    case 1:
                        return new Routing[] {
                            new Routing(0, 0, Math.sqrt(2.) / 2.),
                            new Routing(1, 0, Math.sqrt(2.) / 2.),
                        };
                    case 2:
                        return new Routing[] {
                            new Routing(0, 0, 1.), new Routing(1, 1, 1.),
                        };
                    case 4:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(0, 3, 1.),
                            new Routing(1, 1, 1.),
                            new Routing(1, 2, 1.),
                        };
                    case 8:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(1, 1, 1.),
                            new Routing(1, 2, 1.),
                            new Routing(1, 3, 1.),
                            new Routing(1, 4, 1.),
                            new Routing(0, 5, 1.),
                            new Routing(0, 6, 1.),
                            new Routing(0, 7, 1.),
                        };
                }
            case 4:
                switch (io[1]) {
                    case 1:
                        return new Routing[] {
                            new Routing(0, 0, 0.5),
                            new Routing(1, 0, 0.5),
                            new Routing(2, 0, 0.5),
                            new Routing(3, 0, 0.5),
                        };
                    case 2:
                        return new Routing[] {
                            new Routing(0, 0, Math.sqrt(2.) / 2.),
                            new Routing(1, 1, Math.sqrt(2.) / 2.),
                            new Routing(2, 1, Math.sqrt(2.) / 2.),
                            new Routing(3, 0, Math.sqrt(2.) / 2.),
                        };
                    case 4:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(1, 1, 1.),
                            new Routing(2, 2, 1.),
                            new Routing(3, 3, 1.),
                        };
                    case 8:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(1, 1, 1.),
                            new Routing(1, 2, 1.),
                            new Routing(2, 3, 1.),
                            new Routing(2, 4, 1.),
                            new Routing(3, 5, 1.),
                            new Routing(3, 6, 1.),
                            new Routing(0, 7, 1.),
                        };
                }
            case 8:
                switch (io[1]) {
                    case 1:
                        return new Routing[] {
                            new Routing(0, 0, Math.sqrt(2.) / 4.),
                            new Routing(1, 0, Math.sqrt(2.) / 4.),
                            new Routing(2, 0, Math.sqrt(2.) / 4.),
                            new Routing(3, 0, Math.sqrt(2.) / 4.),
                            new Routing(4, 0, Math.sqrt(2.) / 4.),
                            new Routing(5, 0, Math.sqrt(2.) / 4.),
                            new Routing(6, 0, Math.sqrt(2.) / 4.),
                            new Routing(7, 0, Math.sqrt(2.) / 4.),
                        };
                    case 2:
                        return new Routing[] {
                            new Routing(0, 0, 0.5),
                            new Routing(1, 1, 0.5),
                            new Routing(2, 1, 0.5),
                            new Routing(3, 1, 0.5),
                            new Routing(4, 1, 0.5),
                            new Routing(5, 0, 0.5),
                            new Routing(6, 0, 0.5),
                            new Routing(7, 0, 0.5),
                        };
                    case 4:
                        return new Routing[] {
                            new Routing(0, 0, Math.sqrt(2.) / 2.),
                            new Routing(1, 1, Math.sqrt(2.) / 2.),
                            new Routing(2, 1, Math.sqrt(2.) / 2.),
                            new Routing(3, 2, Math.sqrt(2.) / 2.),
                            new Routing(4, 2, Math.sqrt(2.) / 2.),
                            new Routing(5, 3, Math.sqrt(2.) / 2.),
                            new Routing(6, 3, Math.sqrt(2.) / 2.),
                            new Routing(7, 0, Math.sqrt(2.) / 2.),
                        };
                    case 8:
                        return new Routing[] {
                            new Routing(0, 0, 1.),
                            new Routing(1, 1, 1.),
                            new Routing(2, 2, 1.),
                            new Routing(3, 3, 1.),
                            new Routing(4, 4, 1.),
                            new Routing(5, 5, 1.),
                            new Routing(6, 6, 1.),
                            new Routing(7, 7, 1.),
                        };
                }
        }
        return new Routing[0];
    }

    @Override
    public State get_state() {
        return null;
    }

    public void set_destination_server(DspReceiveServer destination) {
        if (this.target_subscription != null) {
            this.target_subscription.unsubscribe();
        }
        this.destination_server = destination;
        if (destination != null) {
            ModuleServer target_module = (ModuleServer) destination.get_parent_server();
            Subscription target_subscription =
                new DspSettingsChangedSubscription(this,
                    target_module.get_dsp_settings_server());
            target_subscription.subscribe();
            this.make_request(this, "dumpmeta", null);
        }
    }
}
