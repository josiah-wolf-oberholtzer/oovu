package oovu.servers;

import oovu.messaging.MaxIO;
import oovu.messaging.Response;

import com.cycling74.max.Atom;

public class PropertyServer extends AttributeServer {
    public static PropertyServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        PropertyServer property_server =
            (PropertyServer) ModuleMemberServer.allocate_from_label("PropertyServer",
                module_id, desired_name);
        property_server.configure(argument_list);
        return property_server;
    }

    public PropertyServer(ModuleServer module_server) {
        super(module_server);
    }

    @Override
    public void reoutput_value() {
        Atom[] value = this.get_value();
        this.set_value(value);
        Atom[][] payload = MaxIO.to_atoms("value", this.get_value());
        Response response = new Response(this, payload, null);
        this.handle_response(response);
    }
}
