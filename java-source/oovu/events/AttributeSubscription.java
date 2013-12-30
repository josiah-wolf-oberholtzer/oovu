package oovu.events;

import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class AttributeSubscription extends BindingSubscription {
    static public AttributeSubscription from_atoms(
        Server subscriber,
        Atom[] atoms) {
        Map<String, Atom[]> map = MaxIO.from_serialized_dict(atoms);
        Atom[] args = new Atom[0];
        String message_name = null;
        String subscription_name = null;
        String source = null;
        if (map.containsKey("message")) {
            message_name = map.get("message")[0].toString();
        } else {
            message_name = "value";
        }
        MessageHandler message_handler =
            subscriber.get_message_handler(message_name);
        if ((message_handler == null)
            || message_name.equals(message_handler.get_getter_name())) {
            return null;
        } else if (!message_handler.get_is_binding_relevant()) {
            return null;
        }
        if (map.containsKey("name")) {
            subscription_name = map.get("name")[0].toString();
        } else {
            subscription_name = message_name;
        }
        if (map.containsKey("args")) {
            args = map.get("args");
        }
        if (!map.containsKey("source")) {
            return null;
        } else {
            source = map.get("source")[0].toString();
        }
        return new AttributeSubscription(args, subscriber, message_name,
            source, subscription_name);
    }

    public final String osc_address_string;

    public AttributeSubscription(
        Atom[] arguments,
        Server subscriber,
        String message_name,
        String osc_address_string,
        String subscription_name) {
        super(subscriber, ValueEvent.class, new OscAddressFilter(
            osc_address_string), message_name, arguments, subscription_name);
        this.osc_address_string = osc_address_string;
    }

    @Override
    public void handle_event(Event event) {
        // TODO Auto-generated method stub
    }

    @Override
    public Atom[] to_atoms() {
        Map<String, Atom[]> map = new HashMap<String, Atom[]>();
        if (0 < this.arguments.length) {
            map.put("args", this.arguments);
        }
        map.put("message", Atom.parse(this.message_name));
        map.put("name", Atom.parse(this.subscription_name));
        map.put("source", Atom.parse(this.osc_address_string));
        return MaxIO.to_serialized_dict(map);
    }
}
