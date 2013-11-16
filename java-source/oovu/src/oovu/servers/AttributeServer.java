package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.datatypes.AudioSendDatatype;
import oovu.datatypes.Datatype;
import oovu.datatypes.GenericDatatype;
import oovu.events.Event;
import oovu.events.EventTypes;
import oovu.messaging.GetterMessageHandler;
import oovu.messaging.MessageHandler;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.messaging.SetterMessageHandler;
import oovu.patterns.Pattern;
import oovu.states.State;
import oovu.states.StateComponent;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;

abstract public class AttributeServer extends ModuleMemberServer implements
    Comparable<AttributeServer> {

    private class GetPriorityMessageHandler extends GetterMessageHandler {

        @Override
        public String get_name() {
            return "getpriority";
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

        @Override
        public String get_name() {
            return "getvalue";
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

    private class SetPriorityMessageHandler extends SetterMessageHandler {

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public String get_name() {
            return "priority";
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

        @Override
        public Integer get_arity() {
            return AttributeServer.this.datatype.get_arity();
        }

        @Override
        public String get_name() {
            return "value";
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

    protected Integer priority;
    public final Datatype datatype;
    protected Pattern pattern;

    public AttributeServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
        this.datatype = this.setup_datatype();
        this.add_message_handler(new GetPriorityMessageHandler());
        this.add_message_handler(new GetValueMessageHandler());
        this.add_message_handler(new SetPriorityMessageHandler());
        this.add_message_handler(new SetValueMessageHandler());
        this.initialize_value();
        this.initialize_priority();
        if (this.datatype instanceof AudioSendDatatype) {
            Event.add_observer(EventTypes.DSP_RECEIVERS_CHANGED, this);
        }
        if (!(this instanceof ReturnServer)) {
            // this.add_message_handler(new GetPatternMessageHandler());
            // this.add_message_handler(new SetPatternMessageHandler())
        }
    }

    @Override
    protected void cleanup_resources() {
        this.datatype.cleanup_resources();
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

    private void initialize_priority() {
        if (this.argument_map.containsKey("priority")) {
            this.set_priority(this.argument_map.get("priority")[0].getInt());
        } else {
            this.set_priority(0);
        }
    }

    private void initialize_value() {
        if (this.argument_map.containsKey("default")) {
            this.set_value(this.argument_map.get("default"));
        } else {
            this.set_value(this.datatype.get_default());
        }
    }

    abstract public void reoutput_value();

    public void set_pattern(Pattern pattern) {
        if (this.pattern != null) {
            this.pattern.stop_watching_clock(this.pattern);
        }
        this.pattern = pattern;
        if (this.pattern != null) {
            this.pattern.start_watching_clock(this.pattern);
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

    protected Datatype setup_datatype() {
        Atom[] datatype_arguments = this.argument_map.get("datatype");
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
                    this.argument_map);
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
            datatype = new GenericDatatype(this, this.argument_map);
        }
        return datatype;
    }
}
