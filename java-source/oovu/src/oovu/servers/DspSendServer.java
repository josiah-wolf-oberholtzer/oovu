package oovu.servers;

import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.messaging.GetterMessageHandler;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.messaging.SetterMessageHandler;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSendServer extends ModuleMemberServer {

    private class GetDestinationIDMessageHandler extends
        InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getdestinationid";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("destinationid");
            result[0][1] =
                Atom.newAtom(DspSendServer.this.get_destination_id());
            return result;
        }
    }

    private class GetDestinationMessageHandler extends GetterMessageHandler {

        @Override
        public String get_name() {
            return "getdestination";
        }

        @Override
        public boolean is_meta_relevant() {
            return true;
        }

        @Override
        public boolean is_state_relevant() {
            return true;
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            String destination =
                DspSendServer.this.get_destination_address_string();
            if (destination != null) {
                result[0] = Atom.newAtom(new String[] {
                    "destination", destination
                });
            } else {
                result[0] = Atom.newAtom(new String[] {
                    "destination"
                });
            }
            return result;
        }
    }

    private class GetIOMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getio";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][];
            int[] io = DspSendServer.this.get_io();
            result[0] = Atom.newAtom("io", Atom.newAtom(io));
            return result;
        }
    }

    private class SetDestinationMessageHandler extends SetterMessageHandler {

        @Override
        public void call_after() {
            DspSendServer.this.make_request(DspSendServer.this, "dumpmeta",
                null);
        }

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public String get_name() {
            return "destination";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                String address_string = arguments[0].getString();
                OscAddress destination_address =
                    OscAddress.from_cache(address_string);
                DspReceiveServer destination_server =
                    DspReceiveServer.dsp_receive_servers
                        .get(destination_address);
                DspSendServer.this.set_destination_server(destination_server);
            } else {
                DspSendServer.this.set_destination_server(null);
            }
            return null;
        }
    }

    public static DspSendServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (DspSendServer) ModuleMemberServer.allocate_from_label(
            "DspSendServer", module_id, desired_name, argument_list);
    }

    private DspReceiveServer destination_server;

    public DspSendServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public DspSendServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
        this.destination_server = null;
        this.add_message_handler(new GetDestinationMessageHandler());
        this.add_message_handler(new SetDestinationMessageHandler());
        this.add_message_handler(new GetDestinationIDMessageHandler());
        this.add_message_handler(new GetIOMessageHandler());
    }

    public String get_destination_address_string() {
        if (this.destination_server != null) {
            return this.destination_server.get_osc_address_string();
        }
        return null;
    }

    public Integer get_destination_id() {
        DspReceiveServer destination_server = this.get_destination_server();
        if (destination_server == null) {
            return System.identityHashCode(this);
        }
        return System.identityHashCode(destination_server);
    }

    public DspReceiveServer get_destination_server() {
        return this.destination_server;
    }

    public int[] get_io() {
        int[] io = new int[] {
            0, 0
        };
        io[0] = this.get_output_count();
        if (this.destination_server != null) {
            io[1] = this.destination_server.get_input_count();
        }
        return io;
    }

    public int get_output_count() {
        ModuleServer module_server = (ModuleServer) this.parent_server;
        if (module_server == null) {
            return 0;
        }
        DspSettingsServer dsp_settings_server =
            module_server.get_dsp_settings_server();
        if (dsp_settings_server == null) {
            return 1;
        }
        return dsp_settings_server.get_output_count();
    }

    @Override
    public State get_state() {
        return null;
    }

    public void set_destination_server(DspReceiveServer destination) {
        this.destination_server = destination;
    }
}
