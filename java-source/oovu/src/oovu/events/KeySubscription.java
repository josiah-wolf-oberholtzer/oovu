package oovu.events;

import oovu.servers.Server;

import com.cycling74.max.Atom;

public class KeySubscription extends Subscription {
    public final String subscription_name;
    public final String message_name;
    public final Atom[] arguments;

    public KeySubscription(
        Server subscriber,
        int ascii_number,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, KeyEvent.class, new KeyFilter(ascii_number));
        assert message_name != null;
        assert subscription_name != null;
        assert subscriber.get_message_handler(message_name) != null;
        this.message_name = message_name;
        if (arguments == null) {
            arguments = new Atom[0];
        }
        this.arguments = arguments;
        this.subscription_name = subscription_name;
    }

    @Override
    public void handle_event(Event event) {
        this.subscriber.make_request(null, this.message_name, this.arguments);
    }
}
