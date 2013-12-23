package oovu.addresses.tests;

import oovu.addresses.Environment;
import oovu.addresses.OscAddressNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test__OscAddressNode__matches {
    @Before
    public void setUp() throws Exception {
        Environment.reset();
    }

    @After
    public void tearDown() throws Exception {
        Environment.reset();
    }

    @Test
    public void test_matches_01() {
        OscAddressNode foo = new OscAddressNode("foo");
        Assert.assertTrue(foo.matches("foo"));
        Assert.assertTrue(foo.matches("*"));
        Assert.assertFalse(foo.matches("bar"));
        Assert.assertFalse(foo.matches("foo.*"));
        Assert.assertFalse(foo.matches("*.foo"));
    }

    @Test
    public void test_matches_02() {
        OscAddressNode send_one = new OscAddressNode("send.1");
        Assert.assertTrue(send_one.matches("*"));
        Assert.assertTrue(send_one.matches("send.*"));
        Assert.assertTrue(send_one.matches("*.1"));
        Assert.assertTrue(send_one.matches("*.*"));
        Assert.assertFalse(send_one.matches("send.2"));
        Assert.assertFalse(send_one.matches("*.2"));
        Assert.assertFalse(send_one.matches("foo.1"));
        Assert.assertFalse(send_one.matches("foo.*"));
    }

    @Test
    public void test_matches_03() {
        OscAddressNode axis_orbital = new OscAddressNode("axis.orbital");
        Assert.assertTrue(axis_orbital.matches("*"));
        Assert.assertTrue(axis_orbital.matches("axis.*"));
        Assert.assertTrue(axis_orbital.matches("*.orbital"));
        Assert.assertTrue(axis_orbital.matches("*.*"));
        Assert.assertFalse(axis_orbital.matches("ax.orbital"));
        Assert.assertFalse(axis_orbital.matches("is.orbital"));
        Assert.assertFalse(axis_orbital.matches("asix.orb"));
    }
}
