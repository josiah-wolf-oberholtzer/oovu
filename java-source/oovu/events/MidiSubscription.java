package oovu.events;

import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.messaging.MessageHandler;
import oovu.servers.Server;

import com.cycling74.max.Atom;

public class MidiSubscription extends BindingSubscription {
    static public MidiSubscription from_atoms(Server subscriber, Atom[] atoms) {
        Map<String, Atom[]> map = MaxIO.from_serialized_dict(atoms);
        Atom[] args = new Atom[0];
        Integer channel_number = null;
        Integer controller_number = null;
        String message_name = null;
        String subscription_name = null;
        Integer mode = 0;
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
        if (map.containsKey("controller")) {
            controller_number = map.get("controller")[0].toInt();
        }
        if (map.containsKey("channel")) {
            controller_number = map.get("channel")[0].toInt();
        }
        if (map.containsKey("mode")) {
            mode = map.get("mode")[0].toInt();
        }
        return new MidiSubscription(subscriber, channel_number,
            controller_number, mode, message_name, args, subscription_name);
    }

    public final Integer channel_number;
    public final Integer controller_number;
    public final Integer mode;

    public MidiSubscription(
        Server subscriber,
        Integer channel_number,
        Integer controller_number,
        Integer mode,
        String message_name,
        Atom[] arguments,
        String subscription_name) {
        super(subscriber, MidiEvent.class, new MidiFilter(channel_number,
            controller_number), message_name, arguments, subscription_name);
        this.channel_number = channel_number;
        this.controller_number = controller_number;
        this.mode = mode;
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
        if (this.channel_number != null) {
            map.put("channel", Atom.newAtom(new int[] {
                this.channel_number
            }));
        }
        if (this.controller_number != null) {
            map.put("controller", Atom.newAtom(new int[] {
                this.controller_number
            }));
        }
        map.put("message", Atom.parse(this.message_name));
        map.put("name", Atom.parse(this.subscription_name));
        return MaxIO.to_serialized_dict(map);
    }
}
