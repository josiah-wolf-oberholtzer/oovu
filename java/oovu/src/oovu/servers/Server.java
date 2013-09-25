package oovu.servers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.Binding;
import oovu.clients.ServerClient;
import oovu.environment.Dispatcher;
import oovu.environment.Environment;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;
import oovu.messaging.InterfaceRequest;
import oovu.messaging.InterfaceResponse;
import oovu.messaging.MessageHandler;
import oovu.messaging.Response;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxObject;

abstract public class Server implements Dispatcher {

    private class DumpMetaMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "dumpmeta";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            ArrayList<Atom[]> result = new ArrayList<Atom[]>();
            MessageHandler getMetaMessageHandler = Server.this.message_handlers
                .get("getmeta");
            Atom[] meta = Atom.removeFirst(getMetaMessageHandler.run(
                arguments)[0]);
            for (Atom name : meta) {
                MessageHandler message_handler = Server.this.message_handlers
                    .get(name.toString());
                if (message_handler == null) {
                    continue;
                }
                for (Atom[] subresult : message_handler.run(null)) {
                    result.add(subresult);
                }
            }
            return result.toArray(new Atom[0][]);
        }
    }

    private class GetInterfaceMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getinterface";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String[] message_handler_names = Server.this.message_handlers
                .keySet().toArray(new String[0]);
            Arrays.sort(message_handler_names);
            result[0] = Atom.newAtom("interface",
                Atom.newAtom(message_handler_names));
            return result;
        }
    }

    private class GetMetaMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getmeta";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            ArrayList<Atom> getters = new ArrayList<Atom>();
            String[] message_handler_names = Server.this.message_handlers
                .keySet().toArray(new String[0]);
            Arrays.sort(message_handler_names);
            for (String message_handler_name : message_handler_names) {
                if (message_handler_name.startsWith("get")) {
                    getters.add(Atom.newAtom(message_handler_name));
                }
            }
            result[0] = Atom.newAtom("meta", getters.toArray(new Atom[0]));
            return result;
        }
    }

    private class GetNameMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getname";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom
                .newAtom(new String[] { "name", Server.this.get_name() });
            return result;
        }
    }

    private class GetOscAddressMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "getoscaddress";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom.newAtom(new String[] { "oscaddress",
                Server.this.get_osc_address() });
            return result;
        }
    }

    private class ReportMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "report";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String[] report_pieces = Server.this.get_report_pieces();
            for (String report_piece : report_pieces) {
                MaxObject.post(report_piece + "\n");
            }
            return null;
        }
    }

    private class ShowMessageHandler extends MessageHandler {

        @Override
        public String get_name() {
            return "show";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            for (ServerClient node_proxy : Server.this.node_proxies) {
                node_proxy.getMaxBox().getPatcher().send("front", new Atom[0]);
            }
            return null;
        }
    }

    public static Map<String, Atom[]> process_atom_arguments(Atom[] arguments) {
        HashMap<String, Atom[]> argument_map = new HashMap<String, Atom[]>();
        String current_key = null;
        ArrayList<Atom> current_list = new ArrayList<Atom>();
        for (Atom argument : arguments) {
            if ((current_key == null) && (argument.toString().charAt(0) != ':')) {
                continue;
            }
            if (argument.toString().charAt(0) == ':') {
                if ((current_key != null) && (0 < current_list.size())) {
                    argument_map.put(current_key,
                        current_list.toArray(new Atom[current_list.size()]));
                    current_list.clear();
                }
                current_key = argument.toString().substring(1);
            } else {
                current_list.add(argument);
            }
        }
        if (0 < current_list.size()) {
            argument_map.put(current_key,
                current_list.toArray(new Atom[current_list.size()]));
        }
        return Collections.unmodifiableMap(argument_map);
    }

    public final Map<String, Atom[]> argument_map;

    protected final Set<Binding> bindings = new HashSet<Binding>();

    protected final Map<String, Server> child_nodes = new HashMap<String, Server>();

    protected final Map<String, MessageHandler> message_handlers = new HashMap<String, MessageHandler>();
    
    protected Server parent_node;
    
    protected String name = null;

    protected OscAddressNode osc_address_node = null;

    public final Set<ServerClient> node_proxies = new HashSet<ServerClient>();

    public Server(Map<String, Atom[]> argument_map) {
        if (argument_map != null) {
            this.argument_map = Collections.unmodifiableMap(argument_map);
        } else {
            this.argument_map = null;
        }
        this.add_message_handler(new DumpMetaMessageHandler());
        this.add_message_handler(new GetMetaMessageHandler());
        this.add_message_handler(new GetInterfaceMessageHandler());
        this.add_message_handler(new GetNameMessageHandler());
        this.add_message_handler(new GetOscAddressMessageHandler());
        this.add_message_handler(new ReportMessageHandler());
        this.add_message_handler(new ShowMessageHandler());
    }

    public void add_binding(Binding binding) {
    	this.bindings.add(binding);
    }

    public void add_message_handler(MessageHandler message_handler) {
        this.message_handlers.put(message_handler.get_name(),
            message_handler);
    }

    public void clear() {
    	this.child_nodes.clear();
    	this.bindings.clear();
    	this.parent_node = null;
    }

    abstract protected void deallocate();

    public void deallocate_if_necessary() {
        if (this.get_reference_count() == 0) {
            this.deallocate();
        }
    }

    public String get_name() {
        return this.name;
    }
    
    abstract public String get_osc_address();

    public OscAddressNode get_osc_address_node() {
    	return this.osc_address_node;
    }

    public Server get_parent_node() {
    	return this.parent_node;
    }

    abstract public int get_reference_count();

    public String[] get_report_pieces() {
        return new String[0];
    }

    public void handle_interface_request(InterfaceRequest request) {
        MessageHandler message_handler = this.message_handlers
            .get(request.message_handler_name);
        if (message_handler == null) {
            return;
        }
        Atom[][] payload = message_handler.run(request.payload);
        if (payload == null) {
            return;
        }
        Response response = new InterfaceResponse(this, payload, request);
        request.source.handle_response(response);
    }

    abstract public void register_name(String desired_name);

    public void register_at_osc_address() {
    	String osc_address = this.get_osc_address();
        if (osc_address == null) {
            return;
        }
        OscAddressNode root = Environment.root_osc_address_node;
        this.osc_address_node = root.create_address(
        	OscAddress.from_cache(osc_address), false);
        this.osc_address_node.set_server(this);
    }

    public void remove_binding(Binding binding) {
    	this.bindings.remove(binding);
    }
    
    @Override
    public String toString() {
        return this.getClass() + ": " + this.get_name();
    }
    
    abstract public void unregister_name();
    
    public void unregister_from_osc_address() {
        if (this.get_osc_address() == null) {
            return;
        }
        this.osc_address_node.set_server(null);
        this.osc_address_node.prune();
        this.osc_address_node = null;
        for (Binding binding : this.bindings) {
        	binding.unbind();
        }
    }
}
