package oovu.events;

import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class KeySubscription extends BindingSubscription {
    static public KeySubscription from_atoms(Atom[] arguments) {
        return null;
    }

    public final int ascii_number;

    public KeySubscription(
        AttributeServer subscriber,
        int ascii_number,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, KeyEvent.class, new KeyFilter(ascii_number),
            message_name, arguments, subscription_name);
        this.ascii_number = ascii_number;
    }

    @Override
    public void handle_event(Event event) {
        this.subscriber.make_request(null, this.message_name, this.arguments);
    }

    @Override
    public Atom[] to_atoms() {
        Map<String, Atom[]> map = new HashMap<String, Atom[]>();
        if (0 < this.arguments.length) {
            map.put("args", this.arguments);
        }
        map.put("key", Atom.newAtom(new int[] {
            this.ascii_number
        }));
        map.put("message", Atom.parse(this.message_name));
        map.put("name", Atom.parse(this.subscription_name));
        return MaxIO.to_serialized_dict(map);
    }
}
