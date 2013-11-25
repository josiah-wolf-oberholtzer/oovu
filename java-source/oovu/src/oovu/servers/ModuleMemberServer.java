package oovu.servers;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oovu.Proxy;
import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.messaging.Atoms;
import oovu.messaging.BuiltMessageHandler;
import oovu.messaging.Getter;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.messaging.MessageHandlerBuilder;

import com.cycling74.max.Atom;

public abstract class ModuleMemberServer extends Server {
    protected enum AllocationState {
        NO_ADDRESS,
        ADDRESS_WITHOUT_SERVER,
        ADDRESS_WITH_NONMATCHING_SERVER,
        ADDRESS_WITH_MATCHING_SERVER
    }

    private class GetModuleNameMessageHandler extends InfoGetterMessageHandler {
        public GetModuleNameMessageHandler(Server client) {
            super(client, "getmodulename");
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            if (ModuleMemberServer.this.parent_server == null) {
                return null;
            }
            if (ModuleMemberServer.this.parent_server.get_name() != null) {
                result[0] =
                    Atom.newAtom(new String[] {
                        "modulename",
                        ModuleMemberServer.this.parent_server.get_name()
                    });
            } else {
                result[0] = Atom.newAtom(new String[] {
                    "modulename"
                });
            }
            return result;
        }
    }

    public static final Map<String, Class<?>> member_nodes_by_label;
    static {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        map.put("DspReceiveServer", DspReceiveServer.class);
        map.put("DspSendServer", DspSendServer.class);
        map.put("DspSettingsServer", DspSettingsServer.class);
        map.put("MethodServer", MethodServer.class);
        map.put("PropertyServer", PropertyServer.class);
        map.put("ReturnServer", ReturnServer.class);
        member_nodes_by_label = Collections.unmodifiableMap(map);
    }

    public static ModuleMemberServer allocate_from_label(
        String label,
        Integer module_id,
        String desired_name) {
        Class<?> member_node_class =
            ModuleMemberServer.member_nodes_by_label.get(label);
        if (member_node_class == null) {
            member_node_class = PropertyServer.class;
        }
        ModuleServer module_server = ModuleServer.allocate(module_id);
        OscAddress osc_address = OscAddress.from_cache(desired_name);
        if (osc_address.has_parent_path_tokens
            || osc_address.has_wildcard_tokens || !osc_address.is_relative) {
            throw new RuntimeException("Bad child address: " + desired_name);
        }
        ModuleMemberServer member_server = null;
        OscAddressNode osc_address_node =
            module_server.get_osc_address_node().search_for_one(osc_address);
        boolean server_is_new = false;
        if (osc_address_node == null) {
            // address doesn't exist
            member_server =
                ModuleMemberServer.allocate_new_from_label(module_server,
                    label, module_id);
            osc_address_node =
                module_server.get_osc_address_node().create_address(
                    osc_address, true);
            member_server.attach_to_osc_address_node(osc_address_node);
            server_is_new = true;
        } else if (osc_address_node.get_server() == null) {
            // address does exist but no server is attached
            member_server =
                ModuleMemberServer.allocate_new_from_label(module_server,
                    label, module_id);
            member_server.attach_to_osc_address_node(osc_address_node);
            server_is_new = true;
        } else {
            // address exists and a server is already attached
            Server current_member_server = osc_address_node.get_server();
            if (current_member_server.getClass() == member_node_class) {
                // server is of the desired type
                member_server = (ModuleMemberServer) current_member_server;
                server_is_new = false;
            } else {
                // server is not of the desired type, so acquire a new address
                member_server =
                    ModuleMemberServer.allocate_new_from_label(module_server,
                        label, module_id);
                osc_address_node =
                    module_server.get_osc_address_node().create_address(
                        osc_address, true);
                member_server.attach_to_osc_address_node(osc_address_node);
                server_is_new = true;
            }
        }
        if (server_is_new) {
            for (Proxy proxy : osc_address_node.get_proxies()) {
                member_server.make_deferred_request(proxy, "dumpmeta", null);
            }
        }
        return member_server;
    }

    private static ModuleMemberServer allocate_new_from_label(
        ModuleServer module_server,
        String label,
        Integer module_id) {
        Class<?> member_node_class =
            ModuleMemberServer.member_nodes_by_label.get(label);
        ModuleMemberServer new_member_node = null;
        try {
            new_member_node =
                (ModuleMemberServer) member_node_class.getDeclaredConstructor(
                    ModuleServer.class).newInstance(module_server);
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
        } catch (InstantiationException e) {
            // e.printStackTrace();
        } catch (IllegalAccessException e) {
            // e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // e.printStackTrace();
        } catch (InvocationTargetException e) {
            // e.printStackTrace();
        }
        if (new_member_node == null) {
            new_member_node = new PropertyServer(module_server);
        }
        return new_member_node;
    }

    protected static AllocationState find_allocation_state(
        Integer module_id,
        String desired_name,
        Class<? extends ModuleMemberServer> server_class) {
        ModuleServer module_server = ModuleServer.allocate(module_id);
        OscAddress osc_address = OscAddress.from_cache(desired_name);
        if (osc_address.has_parent_path_tokens
            || osc_address.has_wildcard_tokens || !osc_address.is_relative) {
            throw new RuntimeException("Bad child address: " + desired_name);
        }
        OscAddressNode osc_address_node =
            module_server.get_osc_address_node().search_for_one(osc_address);
        if (osc_address_node == null) {
            return AllocationState.NO_ADDRESS;
        } else if (osc_address_node.get_server() == null) {
            return AllocationState.ADDRESS_WITHOUT_SERVER;
        } else {
            Server current_member_server = osc_address_node.get_server();
            if (current_member_server.getClass() == server_class) {
                return AllocationState.ADDRESS_WITH_MATCHING_SERVER;
            } else {
                return AllocationState.ADDRESS_WITH_NONMATCHING_SERVER;
            }
        }
    }

    protected boolean is_configured;

    public ModuleMemberServer(ModuleServer module_server) {
        super();
        this.attach_to_parent_server(module_server);
//        this.add_message_handler(new GetModuleNameMessageHandler(this));
        this.add_built_message_handler(new MessageHandlerBuilder("modulename")
            .with_getter(new Getter(){
                @Override
                public Atom[][] execute(
                    BuiltMessageHandler built_message_handler,
                    Atom[] arguments) {
                    ModuleMemberServer module_member_server = (ModuleMemberServer) built_message_handler.client;
                    if (module_member_server.parent_server == null) {
                        return null;
                    }
                    return Atoms.to_atoms(built_message_handler.name, 
                        module_member_server.parent_server.get_name());
                    }
                })
            .build(this));
        this.is_configured = false;
    }
}
