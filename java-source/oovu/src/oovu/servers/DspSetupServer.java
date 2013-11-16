package oovu.servers;

import java.util.Map;

import com.cycling74.max.Atom;

import oovu.states.State;


public class DspSetupServer extends ModuleMemberServer {

    public static DspSetupServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (DspSetupServer) ModuleMemberServer.allocate_from_label(
            "DspSetupServer", module_id, desired_name, argument_list);
    }

    public DspSetupServer(ModuleServer module_server, Atom[] arguments) {
        this(module_server, Server.process_atom_arguments(Atom
            .removeFirst(arguments)));
    }

    public DspSetupServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
    }

    @Override
    public DspSetupServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new DspSetupServer(module_node, argument_map);
    }

    @Override
    public State get_state() {
        return null;
    }
    
    public boolean module_has_audio_receives() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        return false; 
    }
    
    public boolean module_has_audio_sends() {
        ModuleServer module_server = (ModuleServer) this.get_parent_server();
        return false;
        
    }
    
    public boolean input_count_is_static() {
        return false; 
    }

    public boolean output_count_is_static() {
        return false; 
    }
}
