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
import oovu.events.Event;
import oovu.events.Subscriber;
import oovu.events.Subscription;
import oovu.messaging.DeferredRequestCallback;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.messaging.MessageHandler;
import oovu.messaging.MessagePasser;
import oovu.messaging.Request;
import oovu.messaging.Response;
import oovu.states.State;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxBox;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxPatcher;
import com.cycling74.max.MaxSystem;

abstract public class Server implements MessagePasser, Subscriber {

    private class DumpMetaMessageHandler extends MessageHandler {

        public DumpMetaMessageHandler(Server client) {
            super(client, "dumpmeta");
        }

        @Override
        public Integer get_arity() {
            return 0;
        }

        @Override
        public boolean is_binding_relevant() {
            return false;
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            ArrayList<Atom[]> result = new ArrayList<Atom[]>();
            MessageHandler getMetaMessageHandler =
                Server.this.message_handlers.get("getmeta");
            Atom[] meta =
                Atom.removeFirst(getMetaMessageHandler.run(arguments)[0]);
            for (Atom name : meta) {
                MessageHandler message_handler =
                    Server.this.message_handlers.get(name.toString());
                if (message_handler == null) {
                    continue;
                }
                Atom[][] message_handler_run_result = message_handler.run(null);
                if (message_handler_run_result != null) {
                    for (Atom[] subresult : message_handler_run_result) {
                        result.add(subresult);
                    }
                }
            }
            return result.toArray(new Atom[0][]);
        }
    }

    private class GetInterfaceMessageHandler extends InfoGetterMessageHandler {

        public GetInterfaceMessageHandler(Server client) {
            super(client, "getinterface");
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String[] message_handler_names =
                Server.this.message_handlers.keySet().toArray(new String[0]);
            Arrays.sort(message_handler_names);
            result[0] =
                Atom.newAtom("interface", Atom.newAtom(message_handler_names));
            return result;
        }
    }

    private class GetMetaMessageHandler extends InfoGetterMessageHandler {

        public GetMetaMessageHandler(Server client) {
            super(client, "getmeta");
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            ArrayList<Atom> getters = new ArrayList<Atom>();
            for (MessageHandler message_handler : Server.this.message_handlers
                .values()) {
                if (message_handler.is_meta_relevant()) {
                    getters.add(Atom.newAtom(message_handler.get_name()));
                }
            }
            result[0] = Atom.newAtom("meta", getters.toArray(new Atom[0]));
            return result;
        }
    }

    private class GetOscAddressMessageHandler extends InfoGetterMessageHandler {

        public GetOscAddressMessageHandler(Server client) {
            super(client, "getoscaddress");
        }

        @Override
        public String get_name() {
            return "getoscaddress";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String osc_address_string = Server.this.get_osc_address_string();
            if (osc_address_string != null) {
                Atom[][] result = new Atom[1][];
                result[0] = Atom.newAtom(new String[] {
                    "oscaddress",
                });
                return result;
            }
            return null;
        }
    }

    private class GetUniqueIdMessageHandler extends InfoGetterMessageHandler {

        public GetUniqueIdMessageHandler(Server client) {
            super(client, "getuniqueid");
        }

        @Override
        public String get_name() {
            return "getuniqueid";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("uniqueid");
            result[0][1] = Atom.newAtom(System.identityHashCode(Server.this));
            return result;
        }
    }

    private class ReportMessageHandler extends MessageHandler {

        public ReportMessageHandler(Server client) {
            super(client, "report");
        }

        @Override
        public Integer get_arity() {
            return null;
        }

        @Override
        public boolean is_binding_relevant() {
            return false;
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
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

        public ShowMessageHandler(Server client) {
            super(client, "show");
        }

        @Override
        public Integer get_arity() {
            return null;
        }

        @Override
        public String get_name() {
            return "show";
        }

        @Override
        public boolean is_binding_relevant() {
            return false;
        }

        @Override
        public boolean is_meta_relevant() {
            return false;
        }

        @Override
        public boolean is_rampable() {
            return false;
        }

        @Override
        public boolean is_state_relevant() {
            return false;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            for (ServerClient server_client : Server.this.server_clients) {
                MaxBox box = server_client.getMaxBox();
                MaxPatcher patcher = box.getPatcher();
                patcher.send("front", new Atom[0]);
            }
            return null;
        }
    }

    protected final Set<Server> child_servers = new HashSet<Server>();
    protected final Map<Class<? extends Event>, Subscription> subscriptions =
        new HashMap<Class<? extends Event>, Subscription>();
    protected final Map<String, MessageHandler> message_handlers =
        new HashMap<String, MessageHandler>();
    protected String name = null;
    protected Server parent_server = null;
    protected OscAddressNode osc_address_node = null;
    public final Set<ServerClient> server_clients = new HashSet<ServerClient>();

    public Server() {
        this.add_message_handler(new DumpMetaMessageHandler(this));
        this.add_message_handler(new GetMetaMessageHandler(this));
        this.add_message_handler(new GetInterfaceMessageHandler(this));
        this.add_message_handler(new GetOscAddressMessageHandler(this));
        this.add_message_handler(new GetUniqueIdMessageHandler(this));
        this.add_message_handler(new ReportMessageHandler(this));
        this.add_message_handler(new ShowMessageHandler(this));
    }

    public void add_message_handler(MessageHandler message_handler) {
        this.message_handlers.put(message_handler.get_name(), message_handler);
    }

    public void add_subscription(Subscription subscription) {
        this.subscriptions.put(subscription.event_class, subscription);
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

    @Override
    public OscAddress get_osc_address() {
        if (this.get_osc_address_node() == null) {
            return null;
        }
        return this.get_osc_address_node().get_osc_address();
    }

    @Override
    public OscAddressNode get_osc_address_node() {
        return this.osc_address_node;
    }

    @Override
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
    public void handle_event(Event event) {
        Subscription subscription = this.subscriptions.get(event.getClass());
        if (subscription != null) {
            subscription.handle_event(event);
        }
    }

    @Override
    public void handle_request(Request request) {
        if (request == null) {
            return;
        }
        String message_handler_name = request.destination.message_handler_name;
        if (message_handler_name == null) {
            message_handler_name = "value";
        }
        MessageHandler message_handler =
            this.message_handlers.get(message_handler_name);
        if (message_handler == null) {
            return;
        }
        Atom[][] payload = message_handler.run(request.payload);
        if (payload != null) {
            Response response = new Response(this, payload, request);
            if (payload[0][0].equals(Atom.newAtom("value"))) {
                this.handle_response(response);
            }
            request.source.handle_response(response);
        }
        if (request.call_after) {
            message_handler.call_after();
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
            new Request(source, OscAddress.from_cache("./:"
                + message_handler_name), arguments, true);
        DeferredRequestCallback callback =
            new DeferredRequestCallback(this, request);
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
            new Request(source, OscAddress.from_cache("./:"
                + message_handler_name), arguments, true);
        this.handle_request(request);
    }

    public void remove_subscription(Subscription subscription) {
        this.subscriptions.remove(subscription.event_class);
    }

    @Override
    public String toString() {
        return "<" + this.getClass().getSimpleName() + ": "
            + this.get_osc_address() + ">";
    }
}
