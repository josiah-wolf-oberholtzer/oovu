package oovu.addresses;

public interface Addressed {
    public OscAddress get_osc_address();

    public OscAddressNode get_osc_address_node();

    public String get_osc_address_string();
}
