package oovu.events;

import oovu.events.types.ClockEvent;

import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;

public class ClockEventService implements Executable {
    
    private final MaxClock clock;
    private final int granularity = 20;
    private final EventService client;
    
    public ClockEventService(EventService client) {
        this.client = client;
        this.clock = new MaxClock(this);
        this.clock.delay(this.granularity);
    }
    
    public void execute() {
        this.client.publish(new ClockEvent());
        this.clock.delay(this.granularity);
    }
}
