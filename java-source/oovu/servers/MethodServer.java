package oovu.servers;

import com.cycling74.max.Atom;

public class MethodServer extends AttributeServer {
    public static MethodServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        MethodServer method_server =
            (MethodServer) ModuleMemberServer.allocate_from_label("MethodServer",
                module_id, desired_name);
        method_server.configure(argument_list);
        return method_server;
    }

    public MethodServer(ModuleServer module_node) {
        super(module_node);
    }

    @Override
    public void reoutput_value() {
    }
}
