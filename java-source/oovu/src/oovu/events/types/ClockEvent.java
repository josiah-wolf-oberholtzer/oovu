package oovu.events.types;

import oovu.events.Event;

public class ClockEvent extends Event {
    public final double current_time;

    public ClockEvent() {
        this.current_time = System.currentTimeMillis();
    }
}
