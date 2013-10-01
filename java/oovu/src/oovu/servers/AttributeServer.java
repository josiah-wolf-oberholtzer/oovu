package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import oovu.addressing.OscAddress;
import oovu.datatypes.Datatype;
import oovu.datatypes.GenericDatatype;
import oovu.messaging.MessageHandler;
import oovu.messaging.Request;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

abstract public class AttributeServer extends ModuleMemberServer {

    private class GetPriorityMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getpriority";
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

    private class GetValueMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getvalue";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = AttributeServer.this.get_value();
            result[0] = Atom.newAtom("value", result[0]);
            return result;
        }
    }

    private class SetPriorityMessageHandler extends MessageHandler {

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

    private class SetValueMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "value";
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
    public final Datatype datatype;

    public AttributeServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
        this.datatype = this.setup_datatype();
        this.add_message_handler(new GetPriorityMessageHandler());
        this.add_message_handler(new GetValueMessageHandler());
        this.add_message_handler(new SetPriorityMessageHandler());
        this.add_message_handler(new SetValueMessageHandler());
        this.initialize_value();
    }

    @Override
    protected void cleanup_resources() {
        this.datatype.cleanup_resources();
    }

    public Integer get_priority() {
        return this.priority;
    }

    public Atom[] get_value() {
        return this.datatype.get_value();
    }

    public void handle_asynchronous_datatype_value_output(Atom[] output) {
        Atom[][] payload = new Atom[1][];
        payload[0] = Atom.newAtom("value", output);
        Request request = new Request(this,
            OscAddress.from_cache("./:getvalue"), new Atom[0]);
        Response response = new Response(this, payload, request);
        this.handle_response(response);
    }

    private void initialize_value() {
        if (this.argument_map.containsKey("default")) {
            this.set_value(this.argument_map.get("default"));
        } else {
            this.set_value(this.datatype.get_default());
        }
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
            datatype = (Datatype) datatype_class.getDeclaredConstructor(
                AttributeServer.class, Map.class).newInstance(this,
                this.argument_map);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (datatype == null) {
            datatype = new GenericDatatype(this, this.argument_map);
        }
        return datatype;
    }
}
