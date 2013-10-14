package oovu.timing.tests;

import oovu.timing.MultiEnvelope;
import oovu.timing.TimePoint;

import org.junit.Assert;
import org.junit.Test;

public class Test__MultiEnvelope__unlace {

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
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1000., 1.),
            new TimePoint(3000., 2.),
            new TimePoint(6000., 3.)
        }, result[0]);
    }

    @Test
    public void test_02() {
        MultiEnvelope envelope = new MultiEnvelope(null, new double[] {
            0., 1.
        });
        double[] control_values = new double[] {
            1., -1., 1000., 2., -2., 2000., 3., -3., 3000.
        };
        double current_time = 500.;
        TimePoint[][] result = envelope.unlace(control_values, current_time, 2);
        Assert.assertEquals(2, result.length);
        Assert.assertEquals(3, result[0].length);
        Assert.assertEquals(3, result[1].length);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1500., 1.),
            new TimePoint(3500., 2.),
            new TimePoint(6500., 3.)
        }, result[0]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1500., -1.),
            new TimePoint(3500., -2.),
            new TimePoint(6500., -3.)
        }, result[1]);
    }

    @Test
    public void test_03() {
        MultiEnvelope envelope = new MultiEnvelope(null, new double[] {
            0., 1., -0.5
        });
        double[] control_values = new double[] {
            1., -1., 0.5, 1000., 2., -2., 1., 2000., 3., -3., 1.5, 3000., 666.
        };
        double current_time = 250.;
        TimePoint[][] result = envelope.unlace(control_values, current_time, 3);
        Assert.assertEquals(3, result.length);
        Assert.assertEquals(3, result[0].length);
        Assert.assertEquals(3, result[1].length);
        Assert.assertEquals(3, result[2].length);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., 1.),
            new TimePoint(3250., 2.),
            new TimePoint(6250., 3.)
        }, result[0]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., -1.),
            new TimePoint(3250., -2.),
            new TimePoint(6250., -3.)
        }, result[1]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., 0.5),
            new TimePoint(3250., 1.),
            new TimePoint(6250., 1.5)
        }, result[2]);
    }

    @Test
    public void test_04() {
        MultiEnvelope envelope = new MultiEnvelope(null, new double[] {
            0., 1., -0.5
        });
        double[] control_values =
            new double[] {
                1.,
                -1.,
                0.5,
                1000.,
                2.,
                -2.,
                1.,
                2000.,
                3.,
                -3.,
                1.5,
                3000.,
                666.,
                666.
            };
        double current_time = 250.;
        TimePoint[][] result = envelope.unlace(control_values, current_time, 3);
        Assert.assertEquals(3, result.length);
        Assert.assertEquals(3, result[0].length);
        Assert.assertEquals(3, result[1].length);
        Assert.assertEquals(3, result[2].length);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., 1.),
            new TimePoint(3250., 2.),
            new TimePoint(6250., 3.)
        }, result[0]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., -1.),
            new TimePoint(3250., -2.),
            new TimePoint(6250., -3.)
        }, result[1]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., 0.5),
            new TimePoint(3250., 1.),
            new TimePoint(6250., 1.5)
        }, result[2]);
    }

    @Test
    public void test_05() {
        MultiEnvelope envelope = new MultiEnvelope(null, new double[] {
            0., 1., -0.5
        });
        double[] control_values =
            new double[] {
                1.,
                -1.,
                0.5,
                1000.,
                2.,
                -2.,
                1.,
                2000.,
                3.,
                -3.,
                1.5,
                3000.,
                666.,
                666.,
                666.
            };
        double current_time = 250.;
        TimePoint[][] result = envelope.unlace(control_values, current_time, 3);
        Assert.assertEquals(3, result.length);
        Assert.assertEquals(3, result[0].length);
        Assert.assertEquals(3, result[1].length);
        Assert.assertEquals(3, result[2].length);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., 1.),
            new TimePoint(3250., 2.),
            new TimePoint(6250., 3.)
        }, result[0]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., -1.),
            new TimePoint(3250., -2.),
            new TimePoint(6250., -3.)
        }, result[1]);
        Assert.assertArrayEquals(new TimePoint[] {
            new TimePoint(1250., 0.5),
            new TimePoint(3250., 1.),
            new TimePoint(6250., 1.5)
        }, result[2]);
    }
}
