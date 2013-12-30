package oovu.events;

import java.util.HashMap;
import java.util.Map;

import oovu.addresses.Environment;
import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class KeySubscription extends BindingSubscription {
    static public KeySubscription from_atoms(Server subscriber, Atom[] atoms) {
        Map<String, Atom[]> map = MaxIO.from_serialized_dict(atoms);
        Atom[] args = new Atom[0];
        Integer ascii_number = null;
        String message_name = null;
        String subscription_name = null;
        if (map.containsKey("message")) {
            message_name = map.get("message")[0].toString();
        } else {
            message_name = "value";
        }
        MessageHandler message_handler = subscriber.get_message_handler(message_name);
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
        if (map.containsKey("key")) {
            Atom[] values = map.get("key");
            if (values[0].isInt() || values[0].isFloat()) {
                ascii_number = values[0].toInt();
            } else {
                Environment.log("Key: not int or float");
                return null;
            }
        } else {
            Environment.log("Key: not specified");
            return null;
        }
        return new KeySubscription(subscriber, ascii_number, message_name, args,
            subscription_name);
    }

    public final int ascii_number;

    public KeySubscription(
        Server subscriber,
        int ascii_number,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, KeyEvent.class, new KeyFilter(ascii_number), message_name,
            arguments, subscription_name);
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
        return Atom.newAtom("bind/key", MaxIO.to_serialized_dict(map));
    }
}
