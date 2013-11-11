package oovu.patterns.tests;

import oovu.addresses.Environment;
import oovu.patterns.ValueRange;

import org.junit.Test;
import org.junit.Assert;


public class Test__ValueRange {

    @Test
    public void test_01() {
        for (int i = 0, j = 1000; i < j; i++) {
            double low = (Math.random() - 0.5) * 10.;
            double high = (Math.random() - 0.5) * 10.;
            ValueRange value_range = new ValueRange(low, high);
            double result = value_range.execute();
            Environment.log(value_range.low + " " + result + " " + value_range.high);
            Assert.assertTrue(value_range.low <= result);
            Assert.assertTrue(result <= value_range.high);
        }
    }
}
