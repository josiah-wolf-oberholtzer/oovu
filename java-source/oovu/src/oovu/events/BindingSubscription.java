package oovu.events;

import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class BindingSubscription extends Subscription {
    static public BindingSubscription from_atoms(Atom[] arguments) {
        return null;
    }

    String message_name;
    String subscription_name;
    Atom[] arguments;

    public BindingSubscription(
        Server subscriber,
        Class<? extends Event> event_class,
        Filter filter,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, event_class, filter);
        assert message_name != null;
        assert subscription_name != null;
        assert subscriber.get_message_handler(message_name) != null;
        if (arguments == null) {
            arguments = new Atom[0];
        }
        this.arguments = arguments;
        this.message_name = message_name;
        this.subscription_name = subscription_name;
    }
}
