package oovu.events;

import oovu.events.types.ClockEvent;

import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;

public class ClockEventService implements Executable {
    private MaxClock clock;
    private final int granularity = 50;
    private final EventService client;

    public ClockEventService(EventService client) {
        this.client = client;
        try {
            this.clock = new MaxClock(this);
            this.clock.delay(this.granularity);
        } catch (UnsatisfiedLinkError e) {
            this.clock = null;
        }
    }

    @Override
    public void execute() {
        this.client.publish(new ClockEvent());
        if (this.clock != null) {
            this.clock.delay(this.granularity);
        }
    }
}
