package oovu.events;

import oovu.servers.AttributeServer;

import com.cycling74.max.Atom;

public class MidiSubscription extends BindingSubscription {
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
    }

    @Override
    public void handle_event(Event event) {
        // TODO Auto-generated method stub
    }

    @Override
    public Atom[] to_atoms() {
        // TODO Auto-generated method stub
        return null;
    }
}
