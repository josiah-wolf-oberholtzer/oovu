package oovu.timing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxObject;

public class Line extends MaxObject {

    public static class ClockCallback implements Executable {

        @Override
        public void execute() {
            double current_time = System.currentTimeMillis();
            Line[] lines = null;
            synchronized (Line.lock) {
                lines = Line.clock_watchers.toArray(new Line[0]);
            }
            for (Line clock_watcher : lines) {
                clock_watcher.execute(current_time);
            }
            if (0 < Line.clock_watchers.size()) {
                Line.clock.delay(Line.output_granularity);
            }
        }
    }

    public class TimePoint {

        public final double time;
        public final double value;

        public TimePoint(double time, double value) {
            this.time = time;
            this.value = value;
        }
    }

    private static Lock lock = new ReentrantLock();
    private static MaxClock clock = null;
    private static ClockCallback clock_callback;
    private static Set<Line> clock_watchers = new HashSet<Line>();
    private static int output_granularity = 20;
    private ArrayList<TimePoint> time_points;

    public Line(Atom[] arguments) {
        this.declareIO(1, 1);
        this.time_points = new ArrayList<TimePoint>();
        double value = 0.;
        if (0 < arguments.length) {
            value = arguments[0].toDouble();
        }
        double current_time = System.currentTimeMillis();
        this.time_points.add(new TimePoint(current_time, value));
        if (Line.clock_callback == null) {
            Line.clock_callback = new ClockCallback();
        }
    }

    @Override
    public void bang() {
        double current_time = System.currentTimeMillis();
        this.outlet(0, (float) this.find_value_at_time(current_time));
    }

    private void cleanup_time_points(double current_time) {
        while ((1 < this.time_points.size())
            && ((this.time_points.get(1).time) < current_time)) {
            this.time_points.remove(0);
        }
        if ((this.time_points.size() == 2)
            && (this.time_points.get(1).time < current_time)) {
            this.time_points.remove(0);
        }
    }

    public void execute(double current_time) {
        this.outlet(0, this.find_value_at_time(current_time));
        if (this.time_points.size() == 1) {
            this.stop_watching_clock();
        }
    }

    public double find_value_at_time(double current_time) {
        this.cleanup_time_points(current_time);
        if (this.time_points.size() == 1) {
            return this.time_points.get(0).value;
        }
        TimePoint one = this.time_points.get(0);
        TimePoint two = this.time_points.get(1);
        double delta_value = two.value - one.value;
        double delta_time = two.time - one.time;
        double slope = delta_value / delta_time;
        return (slope * (current_time - one.time)) + one.value;
    }

    @Override
    public void inlet(float value) {
        this.stop();
        double current_time = System.currentTimeMillis();
        this.time_points.clear();
        this.time_points.add(new TimePoint(current_time, value));
        this.outlet(0, (double) value);
    }

    @Override
    public void inlet(int value) {
        this.stop();
        double current_time = System.currentTimeMillis();
        this.time_points.clear();
        this.time_points.add(new TimePoint(current_time, value));
        this.outlet(0, (double) value);
    }

    @Override
    public void list(float[] values) {
        this.stop();
        double time = System.currentTimeMillis();
        double value = this.find_value_at_time(time);
        this.time_points.clear();
        this.time_points.add(new TimePoint(time, value));
        for (int i = 0, j = values.length; i < (j - 1); i += 2) {
            value = values[i];
            time = Math.abs(values[i + 1]) + time;
            this.time_points.add(new TimePoint(time, value));
        }
        this.start_watching_clock();
    }

    @Override
    public void notifyDeleted() {
        this.stop_watching_clock();
    }

    private void start_watching_clock() {
        synchronized (Line.lock) {
            Line.clock_watchers.add(this);
            if (Line.clock == null) {
                Line.clock = new MaxClock(Line.clock_callback);
            }
            if (Line.clock_watchers.size() == 1) {
                Line.clock.delay(Line.output_granularity);
            }
        }
    }

    public void stop() {
        this.stop_watching_clock();
    }

    private void stop_watching_clock() {
        synchronized (Line.lock) {
            Line.clock_watchers.remove(this);
            if ((Line.clock_watchers.size() == 0) && (Line.clock != null)) {
                Line.clock.unset();
                Line.clock.release();
                Line.clock = null;
            }
        }
    }
}
