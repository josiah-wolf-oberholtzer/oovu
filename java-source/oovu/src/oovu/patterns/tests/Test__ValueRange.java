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
}
