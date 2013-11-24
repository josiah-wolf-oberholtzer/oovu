package oovu;

import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;
import oovu.clients.ModuleMemberServerClient;
import oovu.servers.DspSettingsServer;
import oovu.servers.ModuleServer;

import com.cycling74.max.Atom;

public class DspSettings extends ModuleMemberServerClient {

    public DspSettings(Atom[] arguments) {
        super(arguments);
    }

    @Override
    public void bind(Atom[] arguments) {
        ModuleServer module_server = ModuleServer.allocate(this.lazy_module_id);
        DspSettingsServer dsp_settings_server =
            module_server.get_dsp_settings_server();
        if (dsp_settings_server.get_osc_address_node() == null) {
            OscAddress osc_address = OscAddress.from_cache("dsp");
            OscAddressNode osc_address_node =
                module_server.get_osc_address_node().create_address(
                    osc_address, true);
            dsp_settings_server.attach_to_osc_address_node(osc_address_node);
        }
        dsp_settings_server.configure(this.lazy_arguments);
        this.server = dsp_settings_server;
        this.server.server_clients.add(this);
        this.server.make_deferred_request(this, "dumpmeta", null);
    }
}
