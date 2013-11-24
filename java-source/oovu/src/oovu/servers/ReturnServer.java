package oovu.servers;

import oovu.messaging.Response;

import com.cycling74.max.Atom;

public class ReturnServer extends AttributeServer {

    public static ReturnServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        return (ReturnServer) ModuleMemberServer.allocate_from_label(
            "ReturnServer", module_id, desired_name, argument_list);
    }

    public ReturnServer(ModuleServer module_node) {
        super(module_node);
    }

    @Override
    public void reoutput_value() {
        Atom[] value = this.get_value();
        this.set_value(value);
        Atom[][] payload = new Atom[1][];
        payload[0] = this.get_value();
        Response response = new Response(this, payload, null);
        this.handle_response(response);
    }
}
