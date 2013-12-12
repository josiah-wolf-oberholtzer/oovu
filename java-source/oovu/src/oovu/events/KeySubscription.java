package oovu.events;

import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class KeySubscription extends BindingSubscription {
    public KeySubscription(
        Server subscriber,
        int ascii_number,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, KeyEvent.class, new KeyFilter(ascii_number),
            message_name, arguments, subscription_name);
    }

    @Override
    public void handle_event(Event event) {
        this.subscriber.make_request(null, this.message_name, this.arguments);
    }
}
