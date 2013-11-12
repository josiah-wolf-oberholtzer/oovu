package oovu.patterns;

import oovu.servers.Server;
import oovu.timing.ClockWatcher;

public class Pattern extends ClockWatcher {

    public double next_event_time = 0;
    public int current_timing_step = 0;
    public int current_value_step = 0;
    public final Server client;
    public final String message;
    public final ValueRange[] timings;
    public final ValueRange[] values;
    public final int arity;

    public Pattern(Server client, String message, ValueRange[] timings,
        ValueRange[] values, int arity) {
        this.client = client;
        this.message = message;
        this.timings = timings;
        this.values = values;
        this.arity = arity;
        this.start_watching_clock(this);
    }

    // public static Pattern from_mapping(Server client, Map<String, Atom[]>
    // arguments) {
    // String message = null;
    // ValueRange[] timings = null;
    // ValueRange[] values = null;
    // int arity = 0;
    // if (arguments.containsKey("message")) {
    //
    // } else {
    //
    // }
    //
    // }
    @Override
    public void execute(double current_time) {
        synchronized (ClockWatcher.class) {
        }
    }

    public void notify_client() {
    }
}
