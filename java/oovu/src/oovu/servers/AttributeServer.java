package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import oovu.Binding;
import oovu.clients.ServerClient;
import oovu.datatypes.Datatype;
import oovu.datatypes.GenericDatatype;
import oovu.environment.InterfaceHandler;
import oovu.messaging.InterfaceRequest;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.messaging.ValueRequest;
import oovu.messaging.ValueResponse;

import com.cycling74.max.Atom;

abstract public class AttributeServer extends ModuleMemberServer {

    private class GetPriorityInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getpriority";
        }

        @Override
        public Atom[][] run(Server node, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            AttributeServer attribute_node = (AttributeServer) node;
            Integer priority = attribute_node.get_priority();
            result[0] = Atom.newAtom(new int[] { priority });
            result[0] = Atom.newAtom("priority", result[0]);
            return result;
        }
    }

    private class GetValueInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getvalue";
        }

        @Override
        public Atom[][] run(Server node, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = AttributeServer.this.get_value();
            return result;
        }
    }

    private class SetPriorityInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "priority";
        }

        @Override
        public Atom[][] run(Server node, Atom[] arguments) {
            AttributeServer attribute_node = (AttributeServer) node;
            Integer priority = null;
            if (0 < arguments.length) {
                priority = arguments[0].toInt();
            }
            attribute_node.set_priority(priority);
            return null;
        }
    }

    protected Integer priority = 0;

    public final Datatype datatype;

    public AttributeServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
        this.datatype = this.setup_datatype();
        this.add_interface_handler(new GetPriorityInterfaceHandler());
        this.add_interface_handler(new GetValueInterfaceHandler());
        this.add_interface_handler(new SetPriorityInterfaceHandler());
        this.initialize_value();
    }

    public Integer get_priority() {
        return this.priority;
    }

    public Atom[] get_value() {
        return this.datatype.get_value();
    }

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        Response response = null;
        if (ValueRequest.class.isInstance(request)) {
            ValueRequest value_request = (ValueRequest) request;
            Atom[][] payload = new Atom[1][value_request.payload.length];
            payload[0] = this.datatype.process_input(value_request.payload);
            response = new ValueResponse(this, payload, value_request);
            this.handle_response(response);
        } else if (InterfaceRequest.class.isInstance(request)) {
            this.handle_interface_request((InterfaceRequest) request);
        }
    }

    @Override
    public void handle_response(Response response) {
        if (response == null) {
            return;
        }
        for (ServerClient node_proxy : this.node_proxies) {
            node_proxy.handle_response(response);
        }
        for (Binding binding : this.bindings) {
            binding.handle_response(response);
        }
        this.module_node.handle_response(response);
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
