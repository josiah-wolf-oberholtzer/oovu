package oovu.states;

import oovu.addressing.OscAddress;

public class ModuleState extends State {

    public final OscAddress module_osc_address;
    public final State[] state_components;

    public ModuleState(OscAddress module_osc_address, State[] state_components) {
        this.module_osc_address = module_osc_address;
        this.state_components = state_components;
    }
}
