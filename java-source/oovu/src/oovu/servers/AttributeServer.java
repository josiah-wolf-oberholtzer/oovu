package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.datatypes.Datatype;
import oovu.datatypes.GenericDatatype;
import oovu.events.BindingSubscription;
import oovu.events.ValueEvent;
import oovu.messaging.BooleanMessageHandlerCallback;
import oovu.messaging.IntegerMessageHandlerCallback;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.states.State;
import oovu.states.StateComponent;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;

abstract public class AttributeServer extends ModuleMemberServer implements
    Comparable<AttributeServer> {
    protected Integer priority = 0;
    public Datatype datatype = null;
    protected Map<String, BindingSubscription> bindings =
        new HashMap<String, BindingSubscription>();

    public AttributeServer(ModuleServer module_server) {
        super(module_server);
        this.configure_priority_message_handler();
        this.configure_value_message_handler();
        if (!(this instanceof ReturnServer)) {
            this.configure_bind_attribute_message_handler();
            this.configure_bind_key_message_handler();
            this.configure_bind_midi_message_handler();
            this.configure_bind_pattern_message_handler();
            this.configure_bindings_message_handler();
            this.configure_unbind_message_handler();
        }
    }

    public void add_binding(BindingSubscription binding) {
        BindingSubscription previous_binding =
            this.bindings.get(binding.subscription_name);
        if (previous_binding != null) {
            this.remove_binding(previous_binding);
        }
        this.bindings.put(binding.subscription_name, binding);
        binding.subscribe();
    }

    @Override
    public int compareTo(AttributeServer other) {
        int priority_comparison = this.priority.compareTo(other.priority);
        if (priority_comparison == 0) {
            return this.get_osc_address_string().compareTo(
                other.get_osc_address_string());
        } else {
            return -1 * priority_comparison;
        }
    }

    public void configure(Atom[] arguments) {
        if (this.is_configured) {
            return;
        }
        Map<String, Atom[]> argument_map =
            MaxIO.from_serialized_dict(arguments);
        Atom[] datatype_arguments = argument_map.get("datatype");
        String datatype_label = null;
        if ((datatype_arguments != null) && (0 < datatype_arguments.length)) {
            datatype_label = datatype_arguments[0].toString();
        }
        Class<?> datatype_class = Datatype.from_label(datatype_label);
        Datatype datatype = null;
        try {
            datatype =
                (Datatype) datatype_class.getDeclaredConstructor(
                    AttributeServer.class, Map.class).newInstance(this,
                    argument_map);
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
        } catch (SecurityException e) {
            // e.printStackTrace();
        } catch (InstantiationException e) {
            // e.printStackTrace();
        } catch (IllegalAccessException e) {
            // e.printStackTrace();
        } catch (InvocationTargetException e) {
            // e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // e.printStackTrace();
        }
        if (datatype == null) {
            datatype = new GenericDatatype(this, argument_map);
        }
        this.datatype = datatype;
        if (argument_map.containsKey("priority")) {
            this.set_priority(argument_map.get("priority")[0].getInt());
        } else {
            this.set_priority(0);
        }
        if (argument_map.containsKey("default")) {
            this.set_value(argument_map.get("default"));
        } else {
            this.set_value(this.datatype.get_default());
        }
        this.is_configured = true;
    }

    private void configure_bind_attribute_message_handler() {
        MessageHandlerBuilder builder =
            new MessageHandlerBuilder("bind/attribute");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                BindingSubscription binding =
                    BindingSubscription.from_atoms(arguments);
                AttributeServer server =
                    (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bind_key_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bind/key");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                BindingSubscription binding =
                    BindingSubscription.from_atoms(arguments);
                AttributeServer server =
                    (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bind_midi_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bind/midi");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                BindingSubscription binding =
                    BindingSubscription.from_atoms(arguments);
                AttributeServer server =
                    (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bind_pattern_message_handler() {
        MessageHandlerBuilder builder =
            new MessageHandlerBuilder("bind/pattern");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                BindingSubscription binding =
                    BindingSubscription.from_atoms(arguments);
                AttributeServer server =
                    (AttributeServer) message_handler.client;
                server.add_binding(binding);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_bindings_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("bindings");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                AttributeServer server =
                    (AttributeServer) message_handler.client;
                ArrayList<Atom[]> result = new ArrayList<Atom[]>();
                result.add(Atom.parse("bindings/count "
                    + server.bindings.size()));
                BindingSubscription[] bindings =
                    server.bindings.values()
                        .toArray(new BindingSubscription[0]);
                for (BindingSubscription binding : bindings) {
                    result.add(binding.to_atoms());
                }
                return result.toArray(new Atom[0][]);
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_priority_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("priority");
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                return MaxIO.to_atoms(built_message_handler.get_name(),
                    attribute_server.get_priority());
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                Integer priority = null;
                if (0 < arguments.length) {
                    priority = arguments[0].toInt();
                }
                attribute_server.set_priority(priority);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_unbind_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("unbind");
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                AttributeServer server =
                    (AttributeServer) message_handler.client;
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
                        server.bindings.values().toArray(
                            new BindingSubscription[0]);
                    for (BindingSubscription binding : bindings) {
                        server.remove_binding(binding);
                    }
                }
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    private void configure_value_message_handler() {
        MessageHandlerBuilder builder = new MessageHandlerBuilder("value");
        builder.with_arity_callback(new IntegerMessageHandlerCallback() {
            @Override
            public Integer execute(MessageHandler built_message_handler) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                return attribute_server.datatype.get_arity();
            }
        });
        builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                built_message_handler.client.make_request(
                    built_message_handler.client, "getvalue", null);
                return null;
            }
        });
        builder.with_is_binding_relevant(true);
        builder
            .with_is_meta_relevant_callback(new BooleanMessageHandlerCallback() {
                @Override
                public boolean execute(MessageHandler built_message_handler) {
                    if (built_message_handler.client instanceof PropertyServer) {
                        return true;
                    }
                    return false;
                }
            });
        builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                Atom[][] result = new Atom[1][];
                result[0] = AttributeServer.this.get_value();
                result[0] =
                    Atom.newAtom(built_message_handler.get_name(), result[0]);
                return result;
            }
        });
        builder.with_is_rampable_callback(new BooleanMessageHandlerCallback() {
            @Override
            public boolean execute(MessageHandler built_message_handler) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                return attribute_server.datatype.is_rampable();
            }
        });
        builder.with_setter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                attribute_server.set_value(arguments);
                return null;
            }
        });
        this.add_message_handler(builder.build(this));
    }

    @Override
    protected void deallocate() {
        super.deallocate();
        this.datatype.cleanup_resources();
    }

    public Integer get_priority() {
        return this.priority;
    }

    @Override
    public State get_state() {
        ArrayList<StateComponent> state_entries =
            new ArrayList<StateComponent>();
        String osc_address_string = this.get_osc_address_string();
        Set<MessageHandler> message_handlers =
            new HashSet<MessageHandler>(this.message_handlers.values());
        for (MessageHandler message_handler : message_handlers) {
            if (message_handler.get_is_state_relevant()
                && (message_handler.getter != null)) {
                Atom[][] getter_payload =
                    message_handler.handle_message(
                        message_handler.get_getter_name(), null);
                for (Atom[] substate : getter_payload) {
                    String substate_address =
                        osc_address_string + "/:" + substate[0].getString();
                    Atom[] payload = Atom.removeFirst(substate);
                    state_entries.add(new StateComponent(substate_address,
                        payload));
                }
            }
        }
        if (this instanceof PropertyServer) {
            state_entries.add(new StateComponent(osc_address_string, this
                .get_value()));
        }
        return new StateComponentAggregate(osc_address_string,
            state_entries.toArray(new StateComponent[0]));
    }

    public Atom[] get_value() {
        return this.datatype.get_value();
    }

    public void handle_asynchronous_datatype_value_output(Atom[] output) {
        Atom[][] payload = new Atom[1][];
        payload[0] = Atom.newAtom("value", output);
        Request request =
            new Request(this, OscAddress.from_cache("./:getvalue"),
                new Atom[0], true);
        Response response = new Response(this, payload, request);
        this.handle_response(response);
    }

    public void remove_binding(BindingSubscription binding) {
        if (this.bindings.get(binding.subscription_name) == binding) {
            this.bindings.remove(binding.subscription_name);
        }
        binding.unsubscribe();
    }

    abstract public void reoutput_value();

    public void set_priority(Integer priority) {
        if (priority == null) {
            priority = 0;
        }
        this.priority = priority;
    }

    public void set_value(Atom[] value) {
        this.datatype.set_value(value);
        ValueEvent value_event = new ValueEvent(this, this.get_value());
        Environment.event_service.publish(value_event);
    }
}
