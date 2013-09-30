package oovu.timewise;

import java.util.ArrayList;

import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxClock;
import com.cycling74.max.MaxObject;

public class Line extends MaxObject implements Executable {

    private class TimePoint {

        public final double time;
        public final double value;

        public TimePoint(double time, double value) {
            this.time = time;
            this.value = value;
        }
    }

    private MaxClock clock = new MaxClock(this);
    private int output_granularity = 20;
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
    }

    @Override
    public void bang() {
        double current_time = System.currentTimeMillis();
        this.outlet(0, (float) this.find_value_at_time(current_time));
    }

    @Override
    public void execute() {
        double current_time = System.currentTimeMillis();
        this.outlet(0, this.find_value_at_time(current_time));
        if (1 < this.time_points.size()) {
            this.clock.delay(this.output_granularity);
        }
    }

    public double find_value_at_time(double current_time) {
        while ((1 < this.time_points.size())
            && ((this.time_points.get(1).time) < current_time)) {
            this.time_points.remove(0);
        }
        if ((this.time_points.size() == 2)
            && (this.time_points.get(1).time < current_time)) {
            this.time_points.remove(0);
        }
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
            time = values[i + 1] + time;
            this.time_points.add(new TimePoint(time, value));
        }
        this.clock.delay(this.output_granularity);
    }

    @Override
    public void notifyDeleted() {
        this.clock.unset();
        this.clock.release();
    }

    public void stop() {
        this.clock.unset();
    }
}
