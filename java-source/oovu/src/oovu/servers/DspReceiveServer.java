package oovu.servers;

import java.util.HashMap;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.events.types.DspReceiveCreatedEvent;
import oovu.events.types.DspReceiveFreedEvent;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspReceiveServer extends ModuleMemberServer {

    public final static Map<OscAddress, DspReceiveServer> dsp_receive_servers =
        new HashMap<OscAddress, DspReceiveServer>();

    public static DspReceiveServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        DspReceiveServer server =
            (DspReceiveServer) ModuleMemberServer.allocate_from_label(
                "DspReceiveServer", module_id, desired_name, argument_list);
        OscAddress osc_address = server.get_osc_address();
        if ((osc_address != null)
            && (!DspReceiveServer.dsp_receive_servers.containsKey(osc_address))) {
            DspReceiveServer.dsp_receive_servers.put(osc_address, server);
            Environment.event_service
                .publish(new DspReceiveCreatedEvent(server));
        }
        return server;
    }

    public DspReceiveServer(ModuleServer module_node,
        Map<String, Atom[]> argument_map) {
        super(module_node, argument_map);
    }

    @Override
    protected void deallocate() {
        DspReceiveServer.dsp_receive_servers.remove(this.get_osc_address());
        super.deallocate();
        Environment.event_service.publish(new DspReceiveFreedEvent(this));
    }

    @Override
    public State get_state() {
        return null;
    }

    @Override
    public void on_parent_notification() {
        DspReceiveServer.dsp_receive_servers.put(this.get_osc_address(), this);
        Environment.event_service.publish(new DspReceiveCreatedEvent(this));
    }
}
