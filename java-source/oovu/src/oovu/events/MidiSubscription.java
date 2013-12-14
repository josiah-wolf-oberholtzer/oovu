package oovu.events;

import java.util.HashMap;
import java.util.Map;

import oovu.messaging.MaxIO;
import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class MidiSubscription extends BindingSubscription {
    static public MidiSubscription from_atoms(Atom[] arguments) {
        return null;
    }

    public final Integer channel_number;
    public final Integer controller_number;
    public final Integer mode;

    public MidiSubscription(
        AttributeServer subscriber,
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
