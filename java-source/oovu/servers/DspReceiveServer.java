package oovu.servers;

import java.util.HashMap;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.events.DspReceiversChangedEvent;
import oovu.events.Event;
import oovu.events.ModuleNameAcquiredEvent;
import oovu.events.PublisherFilter;
import oovu.events.Subscription;
import oovu.states.State;

import com.cycling74.max.Atom;

public class DspReceiveServer extends ModuleMemberServer {
    private class ModuleNameAcquiredSubscription extends Subscription {
        public ModuleNameAcquiredSubscription(Server subscriber) {
            super(subscriber, ModuleNameAcquiredEvent.class, new PublisherFilter(
                subscriber.parent_server));
        }

        @Override
        public void handle_event(Event event) {
            this.unsubscribe();
            DspReceiveServer.dsp_receive_servers.put(this.subscriber.get_osc_address(),
                (DspReceiveServer) this.subscriber);
            Environment.event_service.publish(new DspReceiversChangedEvent(
                this.subscriber));
        }
    }

    public final static Map<OscAddress, DspReceiveServer> dsp_receive_servers =
        new HashMap<OscAddress, DspReceiveServer>();

    public static DspReceiveServer allocate(
        Integer module_id,
        String desired_name,
        Atom[] argument_list) {
        DspReceiveServer server =
            (DspReceiveServer) ModuleMemberServer.allocate_from_label("DspReceiveServer",
                module_id, desired_name);
        OscAddress osc_address = server.get_osc_address();
        if (osc_address != null) {
            DspReceiveServer registered =
                DspReceiveServer.dsp_receive_servers.get(osc_address);
            if (registered != server) {
                DspReceiveServer.dsp_receive_servers.put(osc_address, server);
                Environment.event_service.publish(new DspReceiversChangedEvent(server));
            }
        }
        return server;
    }

    public DspReceiveServer(ModuleServer module_server) {
        super(module_server);
        if (module_server.get_name() == null) {
            Subscription subscription = new ModuleNameAcquiredSubscription(this);
            subscription.subscribe();
        }
    }

    @Override
    protected void deallocate() {
        DspReceiveServer.dsp_receive_servers.remove(this.get_osc_address());
        super.deallocate();
        Environment.event_service.publish(new DspReceiversChangedEvent(this));
    }

    public int get_input_count() {
        ModuleServer module_server = (ModuleServer) this.parent_server;
        if (module_server == null) {
            return 0;
        }
        DspSettingsServer dsp_settings_server = module_server.get_dsp_settings_server();
        if (dsp_settings_server == null) {
            return 1;
        }
        return dsp_settings_server.get_input_count();
    }

    @Override
    public State get_state() {
        return null;
    }
}
