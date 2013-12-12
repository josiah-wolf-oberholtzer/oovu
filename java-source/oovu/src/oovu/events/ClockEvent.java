package oovu.events;

public class ClockEvent extends Event {
    public final double current_time;

    public ClockEvent() {
        this.current_time = System.currentTimeMillis();
    }
}
