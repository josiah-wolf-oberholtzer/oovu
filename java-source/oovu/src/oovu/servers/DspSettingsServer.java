package oovu.servers;

import java.util.Map;

import oovu.states.State;

import com.cycling74.max.Atom;

public class DspSettingsServer extends ModuleMemberServer {

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
    private Integer voice_count = 1;

    public DspSettingsServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public DspSettingsServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
        this.initialize_input_count();
        this.initialize_output_count();
    }

    public int get_input_count() {
        if (this.input_count_is_static()) {
            return this.input_count;
        }
        return this.voice_count;
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

    public void set_voice_count(int voice_count) {
        if ((0 < voice_count) && (voice_count <= 8)) {
            this.voice_count = voice_count;
        }
    }
}
