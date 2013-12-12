package oovu.events;

public class MidiEvent extends Event {
    public int channel;
    public int controller;
    public double value;

    public MidiEvent(int channel, int controller, int value) {
        this.channel = channel;
        this.controller = controller;
        this.value = (value) / 127.;
    }

    @Override
    public String toString() {
        return "MidiEvent [channel=" + this.channel + ", controller="
            + this.controller + ", value=" + this.value + "]";
    }
}
