package oovu.timewise;

import java.util.ArrayList;

import com.cycling74.max.Executable;

public class MultiEnvelope extends ClockWatcher {

    private class TimePoint {

        public final double time;
        public final double value;

        public TimePoint(double time, double value) {
            this.time = time;
            this.value = value;
        }
    }

    private final Executable client;
    private final ArrayList<ArrayList<TimePoint>> envelopes;

    public MultiEnvelope(Executable client, double[] initial_values) {
        this.client = client;
        this.envelopes = new ArrayList<ArrayList<TimePoint>>();
        if (initial_values.length == 0) {
            initial_values = new double[] {
                0.
            };
        }
        double current_time = System.currentTimeMillis();
        for (double initial_value : initial_values) {
            ArrayList<TimePoint> envelope = new ArrayList<TimePoint>();
            TimePoint time_point = new TimePoint(current_time, initial_value);
            envelope.add(time_point);
        }
    }

    private void cleanup_time_points(
        ArrayList<TimePoint> time_points,
        double current_time) {
        while ((1 < time_points.size())
            && ((time_points.get(1).time) < current_time)) {
            time_points.remove(0);
        }
        if ((time_points.size() == 2)
            && (time_points.get(1).time < current_time)) {
            time_points.remove(0);
        }
    }

    public void control_all_envelopes(double[] control_values) {
    }

    public
        void
        control_one_envelope(int envelope_index, double[] control_values) {
    }

    @Override
    public void execute(double current_time) {
        double[] result = new double[this.envelopes.size()];
        for (int i = 0, j = this.envelopes.size(); i < j; i++) {
            ArrayList<TimePoint> envelope = this.envelopes.get(i);
            this.cleanup_time_points(envelope, current_time);
            result[i] = this.find_value_at_time(envelope, current_time);
        }
        if (!this.has_active_envelopes()) {
            ClockWatcher.stop_watching_clock(this);
        }
    }

    private double find_value_at_time(
        ArrayList<TimePoint> time_points,
        double current_time) {
        if (time_points.size() == 1) {
            return time_points.get(0).value;
        }
        TimePoint one = time_points.get(0);
        TimePoint two = time_points.get(1);
        double delta_value = two.value - one.value;
        double delta_time = two.time - one.time;
        double slope = delta_value / delta_time;
        return (slope * (current_time - one.time)) + one.value;
    }

    public boolean has_active_envelopes() {
        for (ArrayList<TimePoint> envelope : this.envelopes) {
            if (1 < envelope.size()) {
                return true;
            }
        }
        return false;
    }

    public void resize(int new_size) {
        if (new_size < 1) {
            return;
        }
        double current_time = System.currentTimeMillis();
        this.envelopes.clear();
        for (int i = 0; i < new_size; i++) {
            ArrayList<TimePoint> envelope = new ArrayList<TimePoint>();
            TimePoint time_point = new TimePoint(current_time, 0);
            envelope.add(time_point);
        }
    }
}
