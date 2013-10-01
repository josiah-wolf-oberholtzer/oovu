package oovu.timewise;

import java.util.ArrayList;

public class MultiEnvelope extends ClockWatcher {

    private class TimePoint {

        public final double time;
        public final double value;

        public TimePoint(double time, double value) {
            this.time = time;
            this.value = value;
        }
    }

    private final EnvelopeHandler client;
    private final ArrayList<ArrayList<TimePoint>> envelopes;

    public MultiEnvelope(EnvelopeHandler client, double[] initial_values) {
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
        double current_time = System.currentTimeMillis();
        if (control_values.length == this.envelopes.size()) {
        } else if (this.envelopes.size() < control_values.length) {
        }
        if (this.has_active_envelopes()) {
            ClockWatcher.start_watching_clock(this);
        } else {
            ClockWatcher.stop_watching_clock(this);
        }
        this.notify_client(current_time);
    }

    public
        void
        control_one_envelope(int envelope_index, double[] control_values) {
        double current_time = System.currentTimeMillis();
        double time = current_time;
        ArrayList<TimePoint> envelope = this.envelopes.get(envelope_index);
        if (control_values.length == 1) {
            envelope.clear();
            envelope.add(new TimePoint(time, control_values[0]));
        } else if (1 < control_values.length) {
            for (int i = 0, j = control_values.length; i < (j - 1); i += 2) {
                time = Math.abs(control_values[i + 1]) + time;
                envelope.add(new TimePoint(time, control_values[i]));
            }
        }
        if (this.has_active_envelopes()) {
            ClockWatcher.start_watching_clock(this);
        } else {
            ClockWatcher.stop_watching_clock(this);
        }
        this.notify_client(current_time);
    }

    @Override
    public void execute(double current_time) {
        double[] result = new double[this.envelopes.size()];
        for (int i = 0, j = this.envelopes.size(); i < j; i++) {
            ArrayList<TimePoint> envelope = this.envelopes.get(i);
            result[i] = this.find_value_at_time(envelope, current_time);
        }
        if (!this.has_active_envelopes()) {
            ClockWatcher.stop_watching_clock(this);
        }
    }

    private double find_value_at_time(
        ArrayList<TimePoint> time_points,
        double current_time) {
        this.cleanup_time_points(time_points, current_time);
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

    private void notify_client(double current_time) {
        ArrayList<TimePoint> envelope = null;
        double[] response = new double[this.envelopes.size()];
        for (int i = 0, j = this.envelopes.size(); i < j; i++) {
            envelope = this.envelopes.get(i);
            response[i] = this.find_value_at_time(envelope, current_time);
        }
        this.client.handle_envelope_response(response);
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

    public TimePoint[][] unlace(
        double[] control_values,
        double current_time,
        int envelope_count) {
        double time = current_time;
        int groups = (int) Math.floor((double) control_values.length
            / (double) (envelope_count + 1));
        TimePoint[][] result = new TimePoint[envelope_count][groups];
        for (int i = 0, j = control_values.length, group_index = 0; i < j; i += envelope_count + 1, group_index++) {
            time += control_values[i + envelope_count];
            for (int envelope_index = 0, l = this.envelopes.size(); envelope_index < l; envelope_index++) {
                result[envelope_index][group_index] = new TimePoint(time,
                    control_values[i + envelope_index]);
            }
        }
        return result;
    }
}
