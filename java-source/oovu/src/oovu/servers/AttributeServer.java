package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.datatypes.Datatype;
import oovu.datatypes.GenericDatatype;
import oovu.messaging.Atoms;
import oovu.messaging.BooleanMessageHandlerCallback;
import oovu.messaging.BuiltMessageHandler;
import oovu.messaging.Getter;
import oovu.messaging.GetterMessageHandler;
import oovu.messaging.IntegerMessageHandlerCallback;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.messaging.Setter;
import oovu.messaging.SetterMessageHandler;
import oovu.states.State;
import oovu.states.StateComponent;
import oovu.states.StateComponentAggregate;
import oovu.timing.Pattern;

import com.cycling74.max.Atom;

abstract public class AttributeServer extends ModuleMemberServer implements
    Comparable<AttributeServer> {
    private class GetPatternMessageHandler extends GetterMessageHandler {
        public GetPatternMessageHandler(Server client) {
            super(client, "getpattern");
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][0];
            Pattern pattern = AttributeServer.this.get_pattern();
            if (pattern != null) {
                result[0] = pattern.to_atoms();
            }
            result[0] = Atom.newAtom("pattern", result[0]);
            return result;
        }
    }

    private class GetPriorityMessageHandler extends GetterMessageHandler {
        public GetPriorityMessageHandler(Server client) {
            super(client, "getpriority");
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
            Atom[][] result = new Atom[1][];
            Integer priority = AttributeServer.this.get_priority();
            result[0] = Atom.newAtom(new int[] {
                priority
            });
            result[0] = Atom.newAtom("priority", result[0]);
            return result;
        }
    }

    private class GetValueMessageHandler extends GetterMessageHandler {
        public GetValueMessageHandler(Server client) {
            super(client, "getvalue");
        }

        @Override
        public boolean is_meta_relevant() {
            if (AttributeServer.this instanceof PropertyServer) {
                return true;
            }
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = AttributeServer.this.get_value();
            result[0] = Atom.newAtom("value", result[0]);
            return result;
        }
    }

    private class SetPatternMessageHandler extends SetterMessageHandler {
        public SetPatternMessageHandler(Server client) {
            super(client, "pattern");
        }

        @Override
        public Integer get_arity() {
            return null;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Pattern pattern = null;
            if (0 < arguments.length) {
                pattern = Pattern.from_atoms(AttributeServer.this, arguments);
            }
            AttributeServer.this.set_pattern(pattern);
            return null;
        }
    }

    private class SetPriorityMessageHandler extends SetterMessageHandler {
        public SetPriorityMessageHandler(Server client) {
            super(client, "priority");
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Integer priority = null;
            if (0 < arguments.length) {
                priority = arguments[0].toInt();
            }
            AttributeServer.this.set_priority(priority);
            return null;
        }
    }

    protected class SetValueMessageHandler extends MessageHandler {
        public SetValueMessageHandler(Server client) {
            super(client, "value");
        }

        @Override
        public Integer get_arity() {
            return AttributeServer.this.datatype.get_arity();
        }

        @Override
        public boolean is_binding_relevant() {
            return AttributeServer.this.datatype.is_binding_relevant();
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
            return AttributeServer.this.datatype.is_rampable();
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            AttributeServer.this.set_value(arguments);
            result[0] = AttributeServer.this.get_value();
            result[0] = Atom.newAtom("value", result[0]);
            return result;
        }
    }

    protected Integer priority = 0;
    public Datatype datatype = null;
    protected Pattern pattern = null;

    public AttributeServer(ModuleServer module_server) {
        super(module_server);
        // this.add_message_handler(new GetPriorityMessageHandler(this));
        // this.add_message_handler(new GetValueMessageHandler(this));
        // this.add_message_handler(new SetPriorityMessageHandler(this));
        // this.add_message_handler(new SetValueMessageHandler(this));
        this.add_built_message_handler(new MessageHandlerBuilder("priority")
            .with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    BuiltMessageHandler built_message_handler,
                    Atom[] arguments) {
                    AttributeServer attribute_server =
                        (AttributeServer) built_message_handler.client;
                    return Atoms.to_atoms(built_message_handler.name,
                        attribute_server.get_priority());
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    BuiltMessageHandler built_message_handler,
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
            }).build(this));
        this.add_built_message_handler(new MessageHandlerBuilder("value")
            .with_arity_callback(new IntegerMessageHandlerCallback() {
                @Override
                public
                    Integer
                    execute(BuiltMessageHandler built_message_handler) {
                    AttributeServer attribute_server =
                        (AttributeServer) built_message_handler.client;
                    return attribute_server.datatype.get_arity();
                }
            })
            .with_is_binding_relevant(true)
            .with_is_meta_relevant_callback(
                new BooleanMessageHandlerCallback() {
                    @Override
                    public boolean execute(
                        BuiltMessageHandler built_message_handler) {
                        if (built_message_handler.client instanceof PropertyServer) {
                            return true;
                        }
                        return false;
                    }
                }).with_getter(new Getter() {
                @Override
                public Atom[][] execute(
                    BuiltMessageHandler built_message_handler,
                    Atom[] arguments) {
                    return null;
                }
            }).with_setter(new Setter() {
                @Override
                public Atom[][] execute(
                    BuiltMessageHandler built_message_handler,
                    Atom[] arguments) {
                    return null;
                }
            }).build(this));
        if (!(this instanceof ReturnServer)) {
            // this.add_message_handler(new GetPatternMessageHandler(this));
            // this.add_message_handler(new SetPatternMessageHandler(this));
            this.add_built_message_handler(new MessageHandlerBuilder("pattern")
                .with_is_state_relevant(true).with_getter(new Getter() {
                    @Override
                    public Atom[][] execute(
                        BuiltMessageHandler built_message_handler,
                        Atom[] arguments) {
                        AttributeServer server =
                            (AttributeServer) built_message_handler.client;
                        Atom[][] result = new Atom[1][0];
                        Pattern pattern = server.get_pattern();
                        if (pattern != null) {
                            result[0] = pattern.to_atoms();
                        }
                        result[0] =
                            Atom.newAtom(built_message_handler.name, result[0]);
                        return result;
                    }
                }).with_setter(new Setter() {
                    @Override
                    public Atom[][] execute(
                        BuiltMessageHandler built_message_handler,
                        Atom[] arguments) {
                        return null;
                    }
                }).build(this));
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
        for (MessageHandler message_handler : this.message_handlers.values()) {
            if (message_handler.is_state_relevant()) {
                for (Atom[] substate : message_handler.run(null)) {
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
