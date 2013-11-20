package oovu.servers;

import java.util.Map;

import oovu.messaging.GetterMessageHandler;
import oovu.messaging.InfoGetterMessageHandler;
import oovu.messaging.SetterMessageHandler;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSettingsServer extends ModuleMemberServer {

    private class GetInputCountMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getinputcount";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("inputcount");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.input_count);
            return result;
        }
    }

    private class GetLimitingMessageHandler extends GetterMessageHandler {

        @Override
        public String get_name() {
            return "getlimiting";
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
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("limiting");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.limiting);
            return result;
        }
    }

    private class GetOutputCountMessageHandler extends InfoGetterMessageHandler {

        @Override
        public String get_name() {
            return "getoutputcount";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("outputcount");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.output_count);
            return result;
        }
    }

    private class GetSendCountMessageHandler extends GetterMessageHandler {

        @Override
        public String get_name() {
            return "getsendcount";
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
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("sendcount");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.send_count);
            return result;
        }
    }

    private class GetVoiceCountMessageHandler extends GetterMessageHandler {

        @Override
        public String get_name() {
            return "getvoicecount";
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
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("voicecount");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.voice_count);
            return result;
        }
    }

    private class SetLimitingMessageHandler extends SetterMessageHandler {

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public String get_name() {
            return "limiting";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                boolean argument = arguments[0].toBoolean();
                DspSettingsServer.this.set_limiting(argument);
            }
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("limiting");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.get_limiting());
            return result;
        }
    }

    private class SetSendCountMessageHandler extends SetterMessageHandler {

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public String get_name() {
            return "sendcount";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                int argument = arguments[0].toInt();
                DspSettingsServer.this.set_send_count(argument);
            }
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("sendcount");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.get_send_count());
            return result;
        }
    }

    private class SetVoiceCountMessageHandler extends SetterMessageHandler {

        @Override
        public Integer get_arity() {
            return 1;
        }

        @Override
        public String get_name() {
            return "voicecount";
        }

        @Override
        public Atom[][] run(Atom[] arguments) {
            if (0 < arguments.length) {
                int argument = arguments[0].toInt();
                DspSettingsServer.this.set_voice_count(argument);
            }
            Atom[][] result = new Atom[1][2];
            result[0][0] = Atom.newAtom("voicecount");
            result[0][1] = Atom.newAtom(DspSettingsServer.this.get_voice_count());
            return result;
        }
    }

    public static DspSettingsServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        DspSettingsServer server =
            (DspSettingsServer) ModuleMemberServer.allocate_from_label(
                "DspSettingsServer", module_id, desired_name, argument_list);
        ModuleServer module = (ModuleServer) server.get_parent_server();
        module.set_dsp_settings_server(server);
        return server;
    }

    private Integer input_count = null;
    private Integer output_count = null;
    private Integer send_count = 1;
    private Integer voice_count = 1;
    private boolean limiting = true;

    public DspSettingsServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public DspSettingsServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
        this.initialize_input_count();
        this.initialize_output_count();
        this.add_message_handler(new GetInputCountMessageHandler());
        this.add_message_handler(new GetLimitingMessageHandler());
        this.add_message_handler(new GetOutputCountMessageHandler());
        this.add_message_handler(new GetSendCountMessageHandler());
        this.add_message_handler(new GetVoiceCountMessageHandler());
        this.add_message_handler(new SetLimitingMessageHandler());
        this.add_message_handler(new SetSendCountMessageHandler());
        this.add_message_handler(new SetVoiceCountMessageHandler());
    }

    public int get_input_count() {
        if (this.input_count_is_static()) {
            return this.input_count;
        }
        return this.voice_count;
    }

    public boolean get_limiting() {
        return this.limiting;
    }

    public int get_output_count() {
        if (this.output_count_is_static()) {
            return this.output_count;
        }
        return this.voice_count;
    }

    @Override
    public State get_state() {
        return null;
    }

    public int get_send_count() {
        return this.send_count;
    }
    
    public int get_voice_count() {
        return this.voice_count;
    }

    public void initialize_input_count() {
        if (this.argument_map.containsKey("inputs")) {
            int input_count = this.argument_map.get("inputs")[0].getInt();
            if ((0 < input_count) && (input_count <= 8)) {
                this.input_count = input_count;
            }
        }
    }

    public void initialize_output_count() {
        if (this.argument_map.containsKey("outputs")) {
            int output_count = this.argument_map.get("outputs")[0].getInt();
            if ((0 < output_count) && (output_count <= 8)) {
                this.output_count = output_count;
            }
        }
    }

    public boolean input_count_is_static() {
        if (this.input_count != null) {
            return true;
        }
        return false;
    }

    public boolean module_has_dsp_receives() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        for (Server server : module_server.child_servers) {
            if (server instanceof DspReceiveServer) {
                return true;
            }
        }
        return false;
    }

    public boolean module_has_dsp_sends() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        for (Server server : module_server.child_servers) {
            if (server instanceof DspSendServer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DspSettingsServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new DspSettingsServer(module_node, argument_map);
    }

    public boolean output_count_is_static() {
        if (this.output_count != null) {
            return true;
        }
        return false;
    }

    public void set_limiting(boolean limiting) {
        this.limiting = limiting;
    }

    public void set_voice_count(int voice_count) {
        if ((0 < voice_count) && (voice_count <= 8)) {
            this.voice_count = voice_count;
        }
    }

    public void set_send_count(int send_count) {
        if ((0 < send_count) && (send_count <= 8)) {
            this.send_count = send_count;
        }
    }
}
