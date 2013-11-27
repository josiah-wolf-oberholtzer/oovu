package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.addresses.OscAddress;
import oovu.datatypes.Datatype;
import oovu.datatypes.GenericDatatype;
import oovu.messaging.Atoms;
import oovu.messaging.BooleanMessageHandlerCallback;
import oovu.messaging.IntegerMessageHandlerCallback;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.states.State;
import oovu.states.StateComponent;
import oovu.states.StateComponentAggregate;
import oovu.timing.Pattern;

import com.cycling74.max.Atom;

abstract public class AttributeServer extends ModuleMemberServer implements
    Comparable<AttributeServer> {
    protected Integer priority = 0;
    public Datatype datatype = null;
    protected Pattern pattern = null;

    public AttributeServer(ModuleServer module_server) {
        super(module_server);
        MessageHandlerBuilder priority_builder =
            new MessageHandlerBuilder("priority");
        priority_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                return Atoms.to_atoms(built_message_handler.get_name(),
                    attribute_server.get_priority());
            }
        });
        priority_builder.with_setter(new MessageHandlerCallback() {
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
        this.add_message_handler(priority_builder.build(this));
        MessageHandlerBuilder value_builder =
            new MessageHandlerBuilder("value");
        value_builder.with_arity_callback(new IntegerMessageHandlerCallback() {
            @Override
            public Integer execute(MessageHandler built_message_handler) {
                AttributeServer attribute_server =
                    (AttributeServer) built_message_handler.client;
                return attribute_server.datatype.get_arity();
            }
        });
        value_builder.with_callback(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                built_message_handler.client.make_request(
                    built_message_handler.client, "getvalue", null);
                return null;
            }
        });
        value_builder.with_is_binding_relevant(true);
        value_builder
            .with_is_meta_relevant_callback(new BooleanMessageHandlerCallback() {
                @Override
                public boolean execute(MessageHandler built_message_handler) {
                    if (built_message_handler.client instanceof PropertyServer) {
                        return true;
                    }
                    return false;
                }
            });
        value_builder.with_getter(new MessageHandlerCallback() {
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
        value_builder
            .with_is_rampable_callback(new BooleanMessageHandlerCallback() {
                @Override
                public boolean execute(MessageHandler built_message_handler) {
                    AttributeServer attribute_server =
                        (AttributeServer) built_message_handler.client;
                    return attribute_server.datatype.is_rampable();
                }
            });
        value_builder.with_setter(new MessageHandlerCallback() {
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
        this.add_message_handler(value_builder.build(this));
        if (!(this instanceof ReturnServer)) {
            MessageHandlerBuilder pattern_builder =
                new MessageHandlerBuilder("pattern");
            pattern_builder.with_is_state_relevant(true).with_getter(
                new MessageHandlerCallback() {
                    @Override
                    public Atom[][] execute(
                        MessageHandler built_message_handler,
                        Atom[] arguments) {
                        AttributeServer server =
                            (AttributeServer) built_message_handler.client;
                        Atom[][] result = new Atom[1][0];
                        Pattern pattern = server.get_pattern();
                        if (pattern != null) {
                            result[0] = pattern.to_atoms();
                        }
                        result[0] =
                            Atom.newAtom(built_message_handler.get_name(),
                                result[0]);
                        return result;
                    }
                });
            pattern_builder.with_setter(new MessageHandlerCallback() {
                @Override
                public Atom[][] execute(
                    MessageHandler built_message_handler,
                    Atom[] arguments) {
                    AttributeServer attribute_server =
                        (AttributeServer) built_message_handler.client;
                    Pattern pattern = null;
                    if (0 < arguments.length) {
                        pattern =
                            Pattern.from_atoms(attribute_server, arguments);
                    }
                    attribute_server.set_pattern(pattern);
                    return null;
                }
            });
            this.add_message_handler(pattern_builder.build(this));
        }
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
        Map<String, Atom[]> argument_map = Atoms.to_map(arguments);
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

    @Override
    protected void deallocate() {
        super.deallocate();
        this.datatype.cleanup_resources();
    }

    public Pattern get_pattern() {
        return this.pattern;
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

    abstract public void reoutput_value();

    public void set_pattern(Pattern pattern) {
        if (pattern != this.pattern) {
            if (this.pattern != null) {
                this.pattern.stop();
            }
            this.pattern = pattern;
            if (this.pattern != null) {
                this.pattern.set_next_event_time(System.currentTimeMillis());
                this.pattern.start();
            }
        }
    }

    public void set_priority(Integer priority) {
        if (priority == null) {
            priority = 0;
        }
        this.priority = priority;
    }

    public void set_value(Atom[] value) {
        this.datatype.set_value(value);
    }
}
