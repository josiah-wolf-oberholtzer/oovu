package oovu.timewise.tests;

import oovu.timewise.MultiEnvelope;
import oovu.timewise.TimePoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class Test__MultiEnvelope__unlace {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_01() {
        MultiEnvelope envelope = new MultiEnvelope(null, new double[] {
            0.
        });
        double[] control_values = new double[] {
            1., 1000., 2., 2000., 3., 3000.
        };
        double current_time = 0.;
        TimePoint[][] result = envelope.unlace(control_values, current_time, 1);
        Assert.assertEquals(1, result.length);
        Assert.assertEquals(3, result[0].length);
        Assert.assertArrayEquals(new TimePoint[]{
            new TimePoint(1000., 1.), new TimePoint(3000., 2.), new TimePoint(6000., 3.)
        }, result[0]);
    }
    
}
