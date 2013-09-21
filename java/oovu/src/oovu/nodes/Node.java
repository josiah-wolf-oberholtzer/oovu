package oovu.nodes;

import java.util.*;
import com.cycling74.max.*;
import oovu.*;
import oovu.environment.Dispatcher;
import oovu.environment.Environment;
import oovu.environment.InterfaceHandler;
import oovu.environment.InterfaceRequest;
import oovu.environment.InterfaceResponse;
import oovu.environment.Response;
import oovu.proxies.NodeProxy;

abstract public class Node implements Dispatcher {

    private class DumpMetaInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "dumpmeta";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            ArrayList<Atom[]> result = new ArrayList<Atom[]>();
            InterfaceHandler getMetaInterfaceHandler = context.interface_handlers
                .get("getmeta");
            Atom[] meta = Atom.removeFirst(getMetaInterfaceHandler.run(context,
                arguments)[0]);
            for (Atom name : meta) {
                InterfaceHandler interface_handler = context.interface_handlers
                    .get(name.toString());
                if (interface_handler == null) {
                    continue;
                }
                for (Atom[] subresult : interface_handler.run(context, null)) {
                    result.add(subresult);
                }
            }
            return result.toArray(new Atom[0][]);
        }
    }

    private class GetInterfaceInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getinterface";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String[] interface_handler_names = context.interface_handlers
                .keySet().toArray(new String[0]);
            Arrays.sort(interface_handler_names);
            result[0] = Atom.newAtom("interface",
                Atom.newAtom(interface_handler_names));
            return result;
        }
    }

    private class GetMetaInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getmeta";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            ArrayList<Atom> getters = new ArrayList<Atom>();
            String[] interface_handler_names = context.interface_handlers
                .keySet().toArray(new String[0]);
            Arrays.sort(interface_handler_names);
            for (String interface_handler_name : interface_handler_names) {
                if (interface_handler_name.startsWith("get")) {
                    getters.add(Atom.newAtom(interface_handler_name));
                }
            }
            result[0] = Atom.newAtom("meta", getters.toArray(new Atom[0]));
            return result;
        }
    }

    private class GetNameInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getname";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom
                .newAtom(new String[] { "name", context.get_name() });
            return result;
        }
    }

    private class GetOscAddressInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "getoscaddress";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            result[0] = Atom.newAtom(new String[] { "oscaddress",
                context.get_osc_address() });
            return result;
        }
    }

    private class ReportInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "report";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            String[] report_pieces = context.get_report_pieces();
            for (String report_piece : report_pieces) {
                MaxObject.post(report_piece + "\n");
            }
            return null;
        }
    }

    private class ShowInterfaceHandler extends InterfaceHandler {

        @Override
        public String get_name() {
            return "show";
        }

        @Override
        public Atom[][] run(Node context, Atom[] arguments) {
            for (NodeProxy node_proxy : context.node_proxies) {
                node_proxy.getMaxBox().getPatcher().send("front", new Atom[0]);
            }
            return null;
        }
    }

    public final Map<String, Atom[]> argument_map;

    public final Set<Binding> bindings = new HashSet<Binding>();

    public final Map<String, Node> child_nodes = new HashMap<String, Node>();

    protected final Map<String, InterfaceHandler> interface_handlers = new HashMap<String, InterfaceHandler>();

    protected String name = null;

    public final Set<NodeProxy> node_proxies = new HashSet<NodeProxy>();

    public Node(Map<String, Atom[]> argument_map) {
        if (argument_map != null) {
            this.argument_map = Collections.unmodifiableMap(argument_map);
        } else {
            this.argument_map = null;
        }
        this.add_interface_handler(new DumpMetaInterfaceHandler());
        this.add_interface_handler(new GetMetaInterfaceHandler());
        this.add_interface_handler(new GetInterfaceInterfaceHandler());
        this.add_interface_handler(new GetNameInterfaceHandler());
        this.add_interface_handler(new GetOscAddressInterfaceHandler());
        this.add_interface_handler(new ReportInterfaceHandler());
        this.add_interface_handler(new ShowInterfaceHandler());
    }

    public static String
        find_unique_name(String desired_name, Set<String> names) {
        if (!names.contains(desired_name)) {
            return desired_name;
        }
        Integer counter = 1;
        String acquired_name = desired_name + '.' + counter.toString();
        while (names.contains(acquired_name)) {
            counter += 1;
            acquired_name = desired_name + '.' + counter.toString();
        }
        return acquired_name;
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

    public void add_interface_handler(InterfaceHandler interface_handler) {
        this.interface_handlers.put(interface_handler.get_name(),
            interface_handler);
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

    abstract public Node get_parent_node();

    abstract public int get_reference_count();

    public String[] get_report_pieces() {
        return new String[0];
    }

    public void handle_interface_request(InterfaceRequest request) {
        InterfaceHandler interface_handler = this.interface_handlers
            .get(request.interface_handler_name);
        if (interface_handler == null) {
            return;
        }
        Atom[][] payload = interface_handler.run(this, request.payload);
        if (payload == null) {
            return;
        }
        Response response = new InterfaceResponse(this, payload, request);
        request.source.handle_response(response);
    }

    abstract public void register_name(String desired_name);

    public void register_osc_address() {
        if (this.get_osc_address() == null) {
            return;
        }
        Environment.osc_addresses.put(this.get_osc_address(), this);
    }

    @Override
    public String toString() {
        return this.getClass() + ": " + this.get_name();
    }

    abstract public void unregister_name();

    public void unregister_osc_address() {
        if (this.get_osc_address() == null) {
            return;
        }
        Environment.osc_addresses.remove(this.get_osc_address());
    }
}
