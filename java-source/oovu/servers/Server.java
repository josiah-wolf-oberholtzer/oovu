package oovu.servers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.Proxy;
import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.clients.ServerClient;
import oovu.events.AttributeSubscription;
import oovu.events.BindingSubscription;
import oovu.events.KeySubscription;
import oovu.events.MidiSubscription;
import oovu.events.PatternSubscription;
import oovu.events.Subscription;
import oovu.maxguis.BindingsGui;
import oovu.messaging.DeferredRequestCallback;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.states.State;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;

abstract public class Server implements MessagePasser {
    private MaxPatcher bindings_patcher = null;
    protected final Set<Server> child_servers = new HashSet<Server>();
    protected final Set<Subscription> subscriptions = new HashSet<Subscription>();
    protected final Map<String, MessageHandler> message_handlers =
        new HashMap<String, MessageHandler>();
    protected String name = null;
    protected Server parent_server = null;
    protected OscAddressNode osc_address_node = null;
    public final Set<ServerClient> server_clients = new HashSet<ServerClient>();
    protected Map<String, BindingSubscription> bindings =
        new HashMap<String, BindingSubscription>();

    public Server() {
        this.configure_bind_attribute_message_handler();
        this.configure_bind_key_message_handler();
        this.configure_bind_midi_message_handler();
        this.configure_bind_pattern_message_handler();
        this.configure_bindables_message_handler();
        this.configure_bindings_message_handler();
        this.configure_bindings_closed_message_handler();
        this.configure_bindings_view_message_handler();
        this.configure_dumpmeta_message_handler();
        this.configure_interface_message_handler();
        this.configure_meta_message_handler();
        this.configure_oscaddress_message_handler();
        this.configure_report_message_handler();
        this.configure_show_message_handler();
        this.configure_unbind_message_handler();
        this.configure_uniqueid_message_handler();
    }

    public void add_binding(BindingSubscription binding) {
        BindingSubscription previous_binding =
            this.bindings.get(binding.subscription_name);
        if (previous_binding != null) {
            this.remove_binding(previous_binding);
        }
        if (binding != null) {
            this.bindings.put(binding.subscription_name, binding);
            binding.subscribe();
        }
    }

    public void add_message_handler(MessageHandler message_handler) {
        if (message_handler.getter != null) {
            this.message_handlers.put(message_handler.get_getter_name(), message_handler);
        }
        if (message_handler.setter != null) {
            this.message_handlers.put(message_handler.get_name(), message_handler);
        }
    }

    public void add_subscription(Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    public void attach_to_osc_address_node(OscAddressNode osc_address_node) {
        this.detach_from_osc_address_node();
        this.osc_address_node = osc_address_node;
        if (osc_address_node != null) {
            this.osc_address_node.set_server(this);
        }
    }

    public void attach_to_parent_server(Server parent_server) {
        this.detach_from_parent_server();
        this.parent_server = parent_server;
        if (parent_server != null) {
            parent_server.child_servers.add(this);
        }
    }

    public void clear() {
        for (Server child_server : this.child_servers.toArray(new Server[0])) {
            child_server.detach_from_parent_server();
        }
        this.detach_from_parent_server();
        for (ServerClient server_client : this.server_clients) {
            server_client.detach_from_server();
        }
        this.detach_from_osc_address_node();
    }

    private void configure_bind_attribute_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bind/attribute");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.make_request(message_handler.client, "getbindings", null);
                return null;
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                AttributeSubscription binding =
                    AttributeSubscription.from_atoms(message_handler.client, arguments);
                AttributeServer server = (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bind_key_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bind/key");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.make_request(message_handler.client, "getbindings", null);
                return null;
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                KeySubscription binding =
                    KeySubscription.from_atoms(message_handler.client, arguments);
                AttributeServer server = (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bind_midi_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bind/midi");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.make_request(message_handler.client, "getbindings", null);
                return null;
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                MidiSubscription binding =
                    MidiSubscription.from_atoms(message_handler.client, arguments);
                AttributeServer server = (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bind_pattern_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bind/pattern");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.make_request(message_handler.client, "getbindings", null);
                return null;
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                PatternSubscription binding =
                    PatternSubscription.from_atoms(message_handler.client, arguments);
                AttributeServer server = (AttributeServer) message_handler.client;
                server.add_binding(binding);
                binding.set_next_event_time(System.currentTimeMillis());
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bindables_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bindables");
        builder.with_is_meta_relevant(true);
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                HashSet<String> result = new HashSet<String>();
                Server server = message_handler.client;
                for (MessageHandler current_message_handler : server.message_handlers
                    .values()) {
                    if (current_message_handler.get_is_binding_relevant()
                        && (current_message_handler.setter != null)) {
                        result.add(current_message_handler.get_name());
                    }
                }
                String[] strings = result.toArray(new String[0]);
                Arrays.sort(strings);
                return MaxIO.to_atoms(message_handler.get_name(), strings);
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bindings_view_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bindings/view");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                if (message_handler.client.bindings_patcher == null) {
                    message_handler.client.bindings_patcher = BindingsGui.build(message_handler.client);
                }
                if (message_handler.client.bindings_patcher != null) {
                    message_handler.client.bindings_patcher.send("front", new Atom[0]);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }
    
    private void configure_bindings_closed_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bindings/closed");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.bindings_patcher = null;
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }
    
    private void configure_bindings_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bindings");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                ArrayList<Atom[]> result = new ArrayList<Atom[]>();
                result.add(Atom.parse("bindings/count "
                    + message_handler.client.bindings.size()));
                BindingSubscription[] bindings =
                    message_handler.client.bindings.values().toArray(
                        new BindingSubscription[0]);
                ArrayList<Atom> binding_names = new ArrayList<Atom>();
                binding_names.add(Atom.newAtom("bindings/names"));
                ArrayList<Atom> binding_dict = new ArrayList<Atom>();
                binding_dict.add(Atom.newAtom("bindings/dict"));
                for (BindingSubscription binding : bindings) {
                    binding_names.add(Atom.newAtom(binding.subscription_name));
                    binding_dict.add(Atom.newAtom(binding.subscription_name + ":"));
                    binding_dict.add(Atom.newAtom("{"));
                    binding_dict.addAll(Arrays.asList(binding.to_atoms()));
                    binding_dict.add(Atom.newAtom("}"));
                }
                result.add(binding_names.toArray(new Atom[0]));
                result.add(binding_dict.toArray(new Atom[0]));
                return result.toArray(new Atom[0][]);
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_dumpmeta_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("dumpmeta");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                ArrayList<Atom[]> result = new ArrayList<Atom[]>();
                String message_name = "getmeta";
                MessageHandler getmeta_message_handler =
                    built_message_handler.client.message_handlers.get(message_name);
                Atom[] meta =
                    Atom.removeFirst(getmeta_message_handler.handle_message(message_name)[0]);
                for (Atom atom : meta) {
                    String name = atom.getString();
                    MessageHandler message_handler =
                        built_message_handler.client.message_handlers.get(name);
                    if (message_handler == null) {
                        continue;
                    }
                    Atom[][] message_handler_run_result =
                        message_handler.handle_message(name);
                    if (message_handler_run_result != null) {
                        for (Atom[] subresult : message_handler_run_result) {
                            result.add(subresult);
                        }
                    }
                }
                return result.toArray(new Atom[0][]);
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_interface_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("interface");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                Atom[][] result = new Atom[1][];
                String[] message_handler_names =
                    Server.this.message_handlers.keySet().toArray(new String[0]);
                Arrays.sort(message_handler_names);
                result[0] =
                    Atom.newAtom(built_message_handler.get_name(),
                        Atom.newAtom(message_handler_names));
                return result;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_meta_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("meta");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                ArrayList<String> getter_names = new ArrayList<String>();
                Set<MessageHandler> message_handlers =
                    new HashSet<MessageHandler>(
                        built_message_handler.client.message_handlers.values());
                for (MessageHandler message_handler : message_handlers) {
                    if (!message_handler.get_is_meta_relevant()) {
                        continue;
                    }
                    String getter_name = message_handler.get_getter_name();
                    getter_names.add(getter_name);
                }
                if (getter_names.contains("getvalue")) {
                    getter_names.remove("getvalue");
                    getter_names.add("getvalue");
                }
                return MaxIO.to_atoms(built_message_handler.get_getter_name(),
                    getter_names.toArray(new String[0]));
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_oscaddress_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("oscaddress");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                String osc_address_string =
                    built_message_handler.client.get_osc_address_string();
                if (osc_address_string != null) {
                    Atom[][] result = new Atom[1][];
                    result[0] = Atom.newAtom(new String[] {
                        built_message_handler.get_name(), osc_address_string
                    });
                    return result;
                }
                return null;
            }
        });
        builder.with_is_meta_relevant(true);
        this.add_message_handler(builder.build(this));
    }

    private void configure_report_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("report");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                String[] report_pieces = built_message_handler.client.get_report_pieces();
                for (String report_piece : report_pieces) {
                    Environment.log(report_piece);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_show_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("show");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                for (ServerClient server_client : built_message_handler.client.server_clients) {
                    MaxBox box = server_client.getMaxBox();
                    MaxPatcher patcher = box.getPatcher();
                    patcher.send("front", new Atom[0]);
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_unbind_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("unbind");
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                message_handler.client.make_request(message_handler.client, "getbindings", null);
                return null;
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(MessageHandler message_handler, Atom[] arguments) {
                AttributeServer server = (AttributeServer) message_handler.client;
                if (0 < arguments.length) {
                    for (Atom atom : arguments) {
                        String subscription_name = atom.getString();
                        BindingSubscription binding =
                            server.bindings.get(subscription_name);
                        if (binding != null) {
                            server.remove_binding(binding);
                        }
                    }
                } else {
                    BindingSubscription[] bindings =
                        server.bindings.values().toArray(new BindingSubscription[0]);
                    for (BindingSubscription binding : bindings) {
                        server.remove_binding(binding);
                    }
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_uniqueid_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("uniqueid");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public
                Atom[][]
                execute(MessageHandler built_message_handler, Atom[] arguments) {
                Atom[][] result = new Atom[1][2];
                result[0][0] = Atom.newAtom(built_message_handler.get_name());
                result[0][1] =
                    Atom.newAtom(System.identityHashCode(built_message_handler.client));
                return result;
            }
        });
        builder.with_is_meta_relevant(true);
        this.add_message_handler(builder.build(this));
    }

    protected void deallocate() {
        Environment.event_service.unsubscribe(this);
        Server parent_server = this.get_parent_server();
        this.clear();
        if (parent_server != null) {
            parent_server.deallocate_if_necessary();
        }
    }

    public void deallocate_if_necessary() {
        if (this.get_reference_count() == 0) {
            this.deallocate();
        }
    }

    public void detach_from_osc_address_node() {
        if (this.osc_address_node != null) {
            this.osc_address_node.relinquish_number();
            this.osc_address_node.set_server(null);
            this.osc_address_node.prune_if_necessary();
        }
        this.osc_address_node = null;
    }

    public void detach_from_parent_server() {
        if (this.parent_server != null) {
            this.parent_server.child_servers.remove(this);
        }
        this.parent_server = null;
    }

    public MessageHandler get_message_handler(String message_name) {
        return this.message_handlers.get(message_name);
    }

    public String get_name() {
        return this.name;
    }

    public OscAddress get_osc_address() {
        if (this.get_osc_address_node() == null) {
            return null;
        }
        return this.get_osc_address_node().get_osc_address();
    }

    public OscAddressNode get_osc_address_node() {
        return this.osc_address_node;
    }

    public String get_osc_address_string() {
        OscAddress osc_address = this.get_osc_address();
        if (osc_address != null) {
            return this.get_osc_address().toString();
        }
        return null;
    }

    public Server get_parent_server() {
        return this.parent_server;
    }

    public int get_reference_count() {
        return this.server_clients.size() + this.child_servers.size();
    }

    public String[] get_report_pieces() {
        return new String[0];
    }

    abstract public State get_state();

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        String message_handler_name = request.destination.message_handler_name;
        if (message_handler_name == null) {
            message_handler_name = "value";
        }
        MessageHandler message_handler = this.message_handlers.get(message_handler_name);
        if (message_handler == null) {
            return;
        }
        Atom[][] payload =
            message_handler.handle_message(message_handler_name, request.payload);
        Response response = new Response(this, payload, request);
        if ((payload != null) && (0 < payload.length)) {
            if (payload[0][0].equals(Atom.newAtom("value"))) {
                this.handle_response(response);
            } else {
                request.source.handle_response(response);
            }
        }
        if (request.call_after) {
            if (message_handler.callback != null) {
                if (!message_handler_name.equals(message_handler.get_getter_name())) {
                    message_handler.callback.execute(message_handler, null);
                }
            }
        }
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        for (ServerClient server_client : this.server_clients) {
            server_client.handle_response(response);
        }
        OscAddressNode osc_address_node = this.get_osc_address_node();
        if (osc_address_node != null) {
            Set<Proxy> proxies = new HashSet<Proxy>(osc_address_node.get_proxies());
            for (Proxy proxy : proxies) {
                proxy.handle_response(response);
            }
        }
        if (this.parent_server != null) {
            this.parent_server.handle_response(response);
        }
    }

    public void make_deferred_request(
        MessagePasser source,
        String message_handler_name,
        Atom[] arguments) {
        if (message_handler_name == null) {
            return;
        }
        Request request =
            new Request(source, OscAddress.from_cache("./:" + message_handler_name),
                arguments, true);
        DeferredRequestCallback callback = new DeferredRequestCallback(this, request);
        try {
            MaxSystem.deferLow(callback);
        } catch (UnsatisfiedLinkError e) {
            // Environment.log(e);
        }
    }

    public void make_request(
        MessagePasser source,
        String message_handler_name,
        Atom[] arguments) {
        if (message_handler_name == null) {
            return;
        }
        Request request =
            new Request(source, OscAddress.from_cache("./:" + message_handler_name),
                arguments, true);
        this.handle_request(request);
    }

    public void remove_binding(BindingSubscription binding) {
        if (this.bindings.get(binding.subscription_name) == binding) {
            this.bindings.remove(binding.subscription_name);
        }
        binding.unsubscribe();
    }

    public void remove_subscription(Subscription subscription) {
        this.subscriptions.remove(subscription);
    }

    @Override
    public String toString() {
        return "<" + this.getClass().getSimpleName() + ": " + this.get_osc_address()
            + ">";
    }
}
