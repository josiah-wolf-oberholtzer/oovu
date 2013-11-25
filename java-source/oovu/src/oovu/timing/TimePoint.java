package oovu.timing;

public class TimePoint {
    public final double time;
    public final double value;

    public TimePoint(double time, double value) {
        this.time = time;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TimePoint other = (TimePoint) obj;
        if (Double.doubleToLongBits(this.time) != Double
            .doubleToLongBits(other.time)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double
            .doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(this.time);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.value);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
