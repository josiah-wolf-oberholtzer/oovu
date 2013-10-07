package oovu.states;

import oovu.addressing.OscAddress;

public class AttributeState extends State {

    public final OscAddress attribute_osc_address;
    public final StateComponent[] state_components;

    public AttributeState(OscAddress attribute_osc_address,
        StateComponent[] state_components) {
        this.attribute_osc_address = attribute_osc_address;
        this.state_components = state_components;
    }
}
