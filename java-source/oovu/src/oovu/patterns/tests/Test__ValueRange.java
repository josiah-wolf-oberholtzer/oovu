package oovu.patterns.tests;

import oovu.patterns.ValueRange;

import org.junit.Assert;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__ValueRange {

    @Test
    public void test_01() {
        for (int i = 0, j = 1000; i < j; i++) {
            double low = (Math.random() - 0.5) * 10.;
            double high = (Math.random() - 0.5) * 10.;
            ValueRange value_range = new ValueRange(low, high);
            double result = value_range.execute();
            Assert.assertTrue(value_range.low <= result);
            Assert.assertTrue(result <= value_range.high);
        }
    }

    @Test
    public void test_02() {
        for (int i = 0, j = 1000; i < j; i++) {
            double low = (Math.random() - 0.5) * 10.;
            double high = (Math.random() - 0.5) * 10.;
            Atom atom = Atom.newAtom(low + ":" + high);
            ValueRange value_range = new ValueRange(atom);
            double result = value_range.execute();
            Assert.assertTrue(value_range.low <= result);
            Assert.assertTrue(result <= value_range.high);
        }
    }
    
    @Test
    public void test_03() {
        ValueRange value_range_a = new ValueRange(0, 1);
        ValueRange value_range_b = new ValueRange(0, 1);
        ValueRange value_range_c = new ValueRange(0);
        ValueRange value_range_d = new ValueRange(0.5, 1.5);
        Assert.assertTrue(value_range_a.equals(value_range_a));
        Assert.assertTrue(value_range_a.equals(value_range_b));
        Assert.assertFalse(value_range_a.equals(value_range_c));
        Assert.assertFalse(value_range_a.equals(value_range_d));
        Assert.assertTrue(value_range_b.equals(value_range_b));
        Assert.assertFalse(value_range_b.equals(value_range_c));
        Assert.assertFalse(value_range_b.equals(value_range_d));
        Assert.assertTrue(value_range_c.equals(value_range_c));
        Assert.assertFalse(value_range_c.equals(value_range_d));
        Assert.assertTrue(value_range_d.equals(value_range_d));
    }
}
