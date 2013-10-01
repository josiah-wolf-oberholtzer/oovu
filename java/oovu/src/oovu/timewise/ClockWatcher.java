package oovu.timewise;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxObject;

abstract public class ClockWatcher {

    public static class ClockCallback implements Executable {

        @Override
        public void execute() {
            double current_time = System.currentTimeMillis();
            ClockWatcher[] current_clock_watchers = null;
            synchronized (ClockWatcher.lock) {
                current_clock_watchers = ClockWatcher.clock_watchers
                    .toArray(new ClockWatcher[0]);
            }
            for (ClockWatcher clock_watcher : current_clock_watchers) {
                clock_watcher.execute(current_time);
            }
            if (0 < ClockWatcher.clock_watchers.size()) {
                ClockWatcher.clock.delay(ClockWatcher.output_granularity);
            }
        }
    }

    protected static final Lock lock = new ReentrantLock();
    protected static MaxClock clock = null;
    protected static final Set<ClockWatcher> clock_watchers = new HashSet<ClockWatcher>();
    protected static final int output_granularity = 20;
    protected static ClockCallback clock_callback = null;

    public void cleanup_resources() {
        this.stop_watching_clock(this);
    }

    abstract public void execute(double current_time);

    protected void start_watching_clock(ClockWatcher clock_watcher) {
        synchronized (ClockWatcher.class) {
            int original_clock_watchers_count = ClockWatcher.clock_watchers
                .size();
            ClockWatcher.clock_watchers.add(clock_watcher);
            if (ClockWatcher.clock_callback == null) {
                ClockWatcher.clock_callback = new ClockCallback();
            }
            if (ClockWatcher.clock == null) {
                ClockWatcher.clock = new MaxClock(ClockWatcher.clock_callback);
            }
            if (original_clock_watchers_count == 0) {
                ClockWatcher.clock.delay(ClockWatcher.output_granularity);
            }
        }
    }

    protected void stop_watching_clock(ClockWatcher clock_watcher) {
        synchronized (ClockWatcher.class) {
            ClockWatcher.clock_watchers.remove(clock_watcher);
            if ((ClockWatcher.clock_watchers.size() == 0)
                && (ClockWatcher.clock != null)) {
                ClockWatcher.clock.unset();
                // ClockWatcher.clock.release();
                MaxObject.post("Freeing Clock");
            }
        }
    }
}
