package oovu.timewise;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;

abstract public class ClockWatcher {

    public static class ClockCallback implements Executable {

        @Override
        public void execute() {
            double current_time = System.currentTimeMillis();
            ClockWatcher[] lines = null;
            synchronized (ClockWatcher.lock) {
                lines = ClockWatcher.clock_watchers
                    .toArray(new ClockWatcher[0]);
            }
            for (ClockWatcher clock_watcher : lines) {
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

    protected static void start_watching_clock(ClockWatcher clock_watcher) {
        synchronized (ClockWatcher.lock) {
            ClockWatcher.clock_watchers.add(clock_watcher);
            if (ClockWatcher.clock == null) {
                ClockWatcher.clock = new MaxClock(ClockWatcher.clock_callback);
            }
            if (ClockWatcher.clock_watchers.size() == 1) {
                ClockWatcher.clock.delay(ClockWatcher.output_granularity);
            }
        }
    }

    protected static void stop_watching_clock(ClockWatcher clock_watcher) {
        synchronized (ClockWatcher.lock) {
            ClockWatcher.clock_watchers.remove(clock_watcher);
            if ((ClockWatcher.clock_watchers.size() == 0)
                && (ClockWatcher.clock != null)) {
                ClockWatcher.clock.unset();
                ClockWatcher.clock.release();
                ClockWatcher.clock = null;
            }
        }
    }

    abstract public void execute(double current_time);
}
