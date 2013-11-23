package oovu.servers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import oovu.Proxy;
import oovu.addresses.Environment;
import oovu.addresses.OscAddressNode;
import oovu.events.types.ModuleNameAcquiredEvent;
import oovu.messaging.Atoms;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;

public class ModuleServer extends Server implements Comparable<ModuleServer> {

    private class GetMembersMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getmembers";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            List<Server> servers =
                new ArrayList<Server>(ModuleServer.this.child_servers);
            String[] names =
                ModuleServer.this.get_relative_server_names(servers);
            result[0] = Atom.newAtom("members", Atom.newAtom(names));
            return result;
        }
    }

    private class GetMethodsMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getmethods";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            List<? extends Server> servers =
                ModuleServer.this.get_child_method_servers();
            String[] names =
                ModuleServer.this.get_relative_server_names(servers);
            result[0] = Atom.newAtom("methods", Atom.newAtom(names));
            return result;
        }
    }

    private class GetNameMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getname";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            String name = ModuleServer.this.get_name();
            if (name != null) {
                Atom[][] result = new Atom[1][];
                result[0] = Atom.newAtom(new String[] {
                    "name", name
                });
                return result;
            }
            return null;
        }
    }

    private class GetPropertiesMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getproperties";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            List<? extends Server> servers =
                ModuleServer.this.get_child_property_servers();
            String[] names =
                ModuleServer.this.get_relative_server_names(servers);
            result[0] = Atom.newAtom("properties", Atom.newAtom(names));
            return result;
        }
    }

    private class GetReturnsMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getreturns";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            List<? extends Server> servers =
                ModuleServer.this.get_child_return_servers();
            String[] names =
                ModuleServer.this.get_relative_server_names(servers);
            result[0] = Atom.newAtom("returns", Atom.newAtom(names));
            return result;
        }
    }

    public static ModuleServer allocate(Integer module_id) {
        boolean server_is_new = false;
        ModuleServer module_server = null;
        OscAddressNode osc_address_node =
            Environment.root_osc_address_node.get_numbered_child(module_id);
        if (osc_address_node != null) {
            Server server = osc_address_node.get_server();
            if (server != null) {
                if (ModuleServer.class.isInstance(server)) {
                    module_server = (ModuleServer) server;
                    server_is_new = false;
                } else {
                    throw new RuntimeException(
                        "Non-module server attached to numbered node.");
                }
            } else {
                module_server = new ModuleServer(module_id, null);
                module_server.attach_to_osc_address_node(osc_address_node);
                server_is_new = true;
            }
        } else {
            osc_address_node = new OscAddressNode(module_id);
            Environment.root_osc_address_node.add_child(osc_address_node);
            module_server = new ModuleServer(module_id, null);
            module_server.attach_to_osc_address_node(osc_address_node);
            server_is_new = true;
        }
        if (server_is_new) {
            for (Proxy proxy : osc_address_node.get_proxies()) {
                module_server.make_deferred_request(proxy, "dumpmeta", null);
            }
        }
        return module_server;
    }

    public final Integer module_id;
    private DspSettingsServer dsp_settings_server;

    public ModuleServer(Atom[] arguments) {
        this(arguments[0].getInt(), Atoms.to_map(Atom
            .removeFirst(arguments)));
    }

    public ModuleServer(Integer module_id, Map<String, Atom[]> argument_map) {
        super(argument_map);
        this.module_id = module_id;
        this.dsp_settings_server = null;
        this.attach_to_parent_server(Environment.root_server);
        this.add_message_handler(new GetMembersMessageHandler());
        this.add_message_handler(new GetMethodsMessageHandler());
        this.add_message_handler(new GetNameMessageHandler());
        this.add_message_handler(new GetPropertiesMessageHandler());
        this.add_message_handler(new GetReturnsMessageHandler());
    }

    public void acquire_name(String desired_name) {
        if (this.name != null) {
            return;
        }
        this.name = this.osc_address_node.acquire_name(desired_name);
        Environment.event_service.publish(new ModuleNameAcquiredEvent(this));
    }

    @Override
    public int compareTo(ModuleServer other) {
        return this.get_osc_address_string().compareTo(
            other.get_osc_address_string());
    }

    public List<MethodServer> get_child_method_servers() {
        ArrayList<MethodServer> method_servers = new ArrayList<MethodServer>();
        for (Server child : this.child_servers) {
            if (child instanceof MethodServer) {
                method_servers.add((MethodServer) child);
            }
        }
        Collections.sort(method_servers);
        return method_servers;
    }

    public List<PropertyServer> get_child_property_servers() {
        ArrayList<PropertyServer> property_servers =
            new ArrayList<PropertyServer>();
        for (Server child : this.child_servers) {
            if (child instanceof PropertyServer) {
                property_servers.add((PropertyServer) child);
            }
        }
        Collections.sort(property_servers);
        return property_servers;
    }

    public List<ReturnServer> get_child_return_servers() {
        ArrayList<ReturnServer> return_servers = new ArrayList<ReturnServer>();
        for (Server child : this.child_servers) {
            if (child instanceof ReturnServer) {
                return_servers.add((ReturnServer) child);
            }
        }
        Collections.sort(return_servers);
        return return_servers;
    }

    public DspSettingsServer get_dsp_settings_server() {
        return this.dsp_settings_server;
    }

    public String[] get_relative_server_names(List<? extends Server> servers) {
        ArrayList<String> names = new ArrayList<String>();
        OscAddressNode this_address_node = this.get_osc_address_node();
        for (Server server : servers) {
            OscAddressNode that_address_node = server.get_osc_address_node();
            String address_string =
                that_address_node
                    .get_relative_osc_address_string(this_address_node);
            if (address_string != null) {
                names.add(address_string);
            }
        }
        Collections.sort(names);
        return names.toArray(new String[0]);
    }

    @Override
    public State get_state() {
        String osc_address_string = this.get_osc_address_string();
        ArrayList<State> attribute_states = new ArrayList<State>();
        for (PropertyServer property : this.get_child_property_servers()) {
            attribute_states.add(property.get_state());
        }
        return new StateComponentAggregate(osc_address_string,
            attribute_states.toArray(new State[0]));
    }

    public void set_dsp_settings_server(DspSettingsServer dsp_settings_server) {
        if (this.dsp_settings_server == null) {
            this.dsp_settings_server = dsp_settings_server;
        } else if (this.dsp_settings_server != dsp_settings_server) {
            throw new RuntimeException();
        }
    }
}
