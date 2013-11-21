package oovu.servers;

import java.util.HashMap;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.events.Event;
import oovu.events.EventHandler;
import oovu.events.PublisherFilter;
import oovu.events.types.DspReceiversChangedEvent;
import oovu.events.types.ModuleNameAcquiredEvent;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspReceiveServer extends ModuleMemberServer {

    private class ModuleNameAcquiredEventHandler extends EventHandler {

        public ModuleNameAcquiredEventHandler(Server client) {
            super(client, ModuleNameAcquiredEvent.class);
        }

        @Override
        public void run(Event event) {
            Environment.event_service.unsubscribe(this.client,
                this.event_class,
                new PublisherFilter(this.client.get_parent_server()));
            DspReceiveServer.dsp_receive_servers.put(
                this.client.get_osc_address(), (DspReceiveServer) this.client);
            Environment.event_service.publish(new DspReceiversChangedEvent(
                this.client));
        }
    }

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
            Environment.event_service.publish(new DspReceiversChangedEvent(
                server));
        }
        return server;
    }

    public DspReceiveServer(ModuleServer module_server,
        Map<String, Atom[]> argument_map) {
        super(module_server, argument_map);
        if (module_server.get_name() == null) {
            this.add_event_handler(new ModuleNameAcquiredEventHandler(this));
            Environment.event_service.subscribe(this,
                ModuleNameAcquiredEvent.class, new PublisherFilter(
                    module_server));
        }
    }

    @Override
    protected void deallocate() {
        DspReceiveServer.dsp_receive_servers.remove(this.get_osc_address());
        super.deallocate();
        Environment.event_service.publish(new DspReceiversChangedEvent(this));
    }

    @Override
    public State get_state() {
        return null;
    }
}
