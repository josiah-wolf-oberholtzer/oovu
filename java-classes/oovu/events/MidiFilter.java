package oovu.events;

public class MidiFilter extends Filter {
    public final Integer channel;
    public final Integer controller;

    public MidiFilter(Integer channel, Integer controller) {
        this.channel = channel;
        this.controller = controller;
    }

    @Override
    public boolean is_valid_event(Event event) {
        if (!(event instanceof MidiEvent)) {
            return false;
        }
        MidiEvent midi_event = (MidiEvent) event;
        if (this.channel != null) {
            if (midi_event.channel != this.channel) {
                return false;
            }
        }
        if (this.controller != null) {
            if (midi_event.controller != this.controller) {
                return false;
            }
        }
        return true;
    }
}
