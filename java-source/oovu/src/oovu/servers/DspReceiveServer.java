package oovu.servers;

import java.util.HashMap;
import java.util.Map;

import oovu.addresses.OscAddress;
import oovu.events.EventThing;
import oovu.events.EventTypes;
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
            EventThing.notify_observers(EventTypes.DSP_RECEIVERS_CHANGED);
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
        EventThing.notify_observers(EventTypes.DSP_RECEIVERS_CHANGED);
    }

    @Override
    public State get_state() {
        return null;
    }

    @Override
    public DspReceiveServer new_instance(
        Integer module_id,
        Map<String, Atom[]> argument_map) {
        ModuleServer module_node = ModuleServer.allocate(module_id);
        return new DspReceiveServer(module_node, argument_map);
    }

    @Override
    public void on_parent_notification() {
        DspReceiveServer.dsp_receive_servers.put(this.get_osc_address(), this);
        EventThing.notify_observers(EventTypes.DSP_RECEIVERS_CHANGED);
    }
}
