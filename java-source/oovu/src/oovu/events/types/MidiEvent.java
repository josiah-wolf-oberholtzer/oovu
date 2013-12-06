package oovu.events.types;

import oovu.events.Event;

public class MidiEvent extends Event {
    public int channel;
    public int controller;
    public int value;

    public MidiEvent(int channel, int controller, int value) {
        this.channel = channel;
        this.controller = controller;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MidiEvent [channel=" + this.channel + ", controller="
            + this.controller + ", value=" + this.value + "]";
    }
}
