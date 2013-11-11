package oovu.patterns;

import java.util.Arrays;

import com.cycling74.max.Atom;

public class ValueRange {

    public final double low;
    public final double high;

    public ValueRange(Atom atom) {
        if (atom.isFloat() || atom.isInt()) {
            this.low = atom.toDouble();
            this.high = atom.toDouble();
        } else {
            String value = atom.getString();
            String[] parts = value.split(":");
            assert parts.length == 2;
            double[] values = new double[2];
            values[0] = Double.parseDouble(parts[0]);
            values[1] = Double.parseDouble(parts[1]);
            Arrays.sort(values);
            this.low = values[0];
            this.high = values[1];
        }
    }

    public ValueRange(double value) {
        this(value, value);
    }

    public ValueRange(double low, double high) {
        double[] values = new double[] {
            low, high
        };
        Arrays.sort(values);
        this.low = values[0];
        this.high = values[1];
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
        ValueRange other = (ValueRange) obj;
        if (Double.doubleToLongBits(this.high) != Double
            .doubleToLongBits(other.high)) {
            return false;
        }
        if (Double.doubleToLongBits(this.low) != Double
            .doubleToLongBits(other.low)) {
            return false;
        }
        return true;
    }

    public double execute() {
        double result = Math.random();
        double spread = this.high - this.low;
        return (result * spread) + this.low;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(this.high);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.low);
        result = (prime * result) + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
