package oovu.events;

import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

abstract public class BindingSubscription extends Subscription {
    static public BindingSubscription from_atoms(Atom[] arguments) {
        return null;
    }

    public final String message_name;
    public final String subscription_name;
    public final Atom[] arguments;

    public BindingSubscription(
        AttributeServer subscriber,
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

    abstract public Atom[] to_atoms();
}
