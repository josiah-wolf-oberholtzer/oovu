package oovu.servers;

import com.cycling74.max.Atom;

public class MethodServer extends AttributeServer {

    public static MethodServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (MethodServer) ModuleMemberServer.allocate_from_label(
            "MethodServer", module_id, desired_name, argument_list);
    }

    public MethodServer(ModuleServer module_node) {
        super(module_node);
    }

    @Override
    public void reoutput_value() {
    }
}
