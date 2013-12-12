package oovu.events;

import oovu.addresses.OscAddress;

public class OscAddressFilter extends Filter {
    public final OscAddress osc_address;

    public OscAddressFilter(String osc_address_string) {
        this.osc_address = OscAddress.from_cache(osc_address_string);
    }

    @Override
    public boolean is_valid_event(Event event) {
        if (!(event instanceof ValueEvent)) {
            return false;
        }
        ValueEvent value_event = (ValueEvent) event;
        OscAddress event_address = value_event.publisher.get_osc_address();
        if (this.osc_address.equals(event_address)) {
            return true;
        }
        return false;
    }
}
