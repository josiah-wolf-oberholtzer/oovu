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
import oovu.messaging.MessageHandler;
import oovu.messaging.MessageHandlerBuilder;
import oovu.messaging.MessageHandlerCallback;
import oovu.states.State;
import oovu.states.StateComponentAggregate;

import com.cycling74.max.Atom;
import com.cycling74.max.MaxPatcher;

public class ModuleServer extends Server implements Comparable<ModuleServer> {
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
        this(arguments[0].getInt(), Atoms.to_map(Atom.removeFirst(arguments)));
    }

    public ModuleServer(Integer module_id, Map<String, Atom[]> argument_map) {
        super();
        this.module_id = module_id;
        this.dsp_settings_server = null;
        this.attach_to_parent_server(Environment.root_server);
        // MEMBERS
        MessageHandlerBuilder members_builder =
            new MessageHandlerBuilder("members");
        members_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                ModuleServer module_server =
                    (ModuleServer) built_message_handler.client;
                List<Server> servers =
                    new ArrayList<Server>(module_server.child_servers);
                String[] names =
                    module_server.get_relative_server_names(servers);
                return Atoms.to_atoms(built_message_handler.get_name(), names);
            }
        });
        this.add_message_handler(members_builder.build(this));
        // METHODS
        MessageHandlerBuilder methods_builder =
            new MessageHandlerBuilder("methods");
        methods_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                ModuleServer module_server =
                    (ModuleServer) built_message_handler.client;
                List<? extends Server> servers =
                    module_server.get_child_method_servers();
                String[] names =
                    module_server.get_relative_server_names(servers);
                return Atoms.to_atoms(built_message_handler.get_name(), names);
            }
        });
        this.add_message_handler(methods_builder.build(this));
        // MIXER
        MessageHandlerBuilder mixer_builder =
            new MessageHandlerBuilder("mixer");
        mixer_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler message_handler,
                Atom[] arguments) {
                ModuleServer server = (ModuleServer) message_handler.client;
                MaxPatcher mixer_patcher = server.build_mixer_patcher();
                mixer_patcher.send("front", new Atom[0]);
                return null;
            }
        });
        this.add_message_handler(mixer_builder.build(this));
        // NAME
        MessageHandlerBuilder name_builder = new MessageHandlerBuilder("name");
        name_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                ModuleServer module_server =
                    (ModuleServer) built_message_handler.client;
                String name = module_server.get_name();
                if (name != null) {
                    Atom[][] result = new Atom[1][];
                    result[0] = Atom.newAtom(new String[] {
                        built_message_handler.get_name(), name
                    });
                    return result;
                }
                return null;
            }
        });
        name_builder.with_is_meta_relevant(true);
        this.add_message_handler(name_builder.build(this));
        // PROPERTIES
        MessageHandlerBuilder properties_builder =
            new MessageHandlerBuilder("properties");
        properties_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                ModuleServer module_server =
                    (ModuleServer) built_message_handler.client;
                List<? extends Server> servers =
                    module_server.get_child_property_servers();
                String[] names =
                    module_server.get_relative_server_names(servers);
                return Atoms.to_atoms(built_message_handler.get_name(), names);
            }
        });
        this.add_message_handler(properties_builder.build(this));
        // RETURNS
        MessageHandlerBuilder returns_builder =
            new MessageHandlerBuilder("returns");
        returns_builder.with_getter(new MessageHandlerCallback() {
            @Override
            public Atom[][] execute(
                MessageHandler built_message_handler,
                Atom[] arguments) {
                ModuleServer module_server =
                    (ModuleServer) built_message_handler.client;
                List<? extends Server> servers =
                    module_server.get_child_return_servers();
                String[] names =
                    module_server.get_relative_server_names(servers);
                return Atoms.to_atoms(built_message_handler.get_name(), names);
            }
        });
        this.add_message_handler(returns_builder.build(this));
    }

    public void acquire_name(String desired_name) {
        if (this.name != null) {
            return;
        }
        this.name = this.osc_address_node.acquire_name(desired_name);
        Environment.event_service.publish(new ModuleNameAcquiredEvent(this));
    }

    public MaxPatcher build_mixer_patcher() {
        DspSettingsServer dsp_settings = this.get_dsp_settings_server();
        boolean has_receives = dsp_settings.module_has_dsp_receives();
        boolean has_sends = dsp_settings.module_has_dsp_sends();
        if ((!has_receives) && (!has_sends)) {
            return null;
        }
        MaxPatcher patcher = new MaxPatcher(0, 0, 150, 720);
        patcher.setBackgroundColor(0, 0, 0);
        this.fill_mixer_patcher(patcher, 5, 5);
        patcher.send("statusbarvisible", Atom.parse("0"));
        patcher.send("toolbarvisible", Atom.parse("0"));
        return patcher;
    }

    @Override
    public int compareTo(ModuleServer other) {
        return this.get_osc_address_string().compareTo(
            other.get_osc_address_string());
    }

    public void fill_mixer_patcher(
        MaxPatcher patcher,
        int x_offset,
        int y_offset) {
        DspSettingsServer dsp_settings = this.get_dsp_settings_server();
        boolean has_receives = dsp_settings.module_has_dsp_receives();
        boolean has_sends = dsp_settings.module_has_dsp_sends();
        if (has_receives || has_sends) {
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " " + y_offset
                    + " 140 110 @name oovu.mixer.title.basic @args "
                    + this.module_id + " @clickthrough 1 @background 1"));
        }
        if (has_receives) {
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " " + y_offset
                    + " 140 110 @name oovu.mixer.title.inputs @args "
                    + this.module_id + " @clickthrough 1"));
        }
        if (has_sends) {
            patcher.newDefault(
                x_offset,
                y_offset,
                "bpatcher",
                Atom.parse("@patching_rect " + x_offset + " " + y_offset
                    + " 140 110 @name oovu.mixer.title.outputs @args "
                    + this.module_id + " @clickthrough 1"));
            patcher
                .newDefault(
                    x_offset,
                    y_offset,
                    "bpatcher",
                    Atom.parse("@patching_rect " + x_offset + " "
                        + (y_offset + 115)
                        + " 140 595 @name oovu.mixer.sends @args "
                        + this.module_id));
        }
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
        if (this.dsp_settings_server == null) {
            this.dsp_settings_server = new DspSettingsServer(this);
        }
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
}
