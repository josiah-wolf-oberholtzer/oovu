package oovu.events;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class AttributeSubscription extends BindingSubscription {
    public AttributeSubscription(
        Server subscriber,
        Class<? extends Event> event_class,
        String osc_address_string,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, event_class,
            new OscAddressFilter(osc_address_string), message_name, arguments,
            subscription_name);
    }

    @Override
    public void handle_event(Event event) {
        // TODO Auto-generated method stub
    }
}
