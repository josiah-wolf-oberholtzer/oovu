package oovu.servers.tests;

import oovu.addressing.Environment;
import oovu.servers.ModuleServer;
import oovu.servers.members.PropertyServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__AttributeServer__priority {

    @Before
    public void setUp() throws Exception {
        Environment.reset();
    }

    @After
    public void tearDown() throws Exception {
        Environment.reset();
    }

    @Test
    public void test_01() {
        PropertyServer property_server = new PropertyServer(
            ModuleServer.allocate(1001), Atom.parse("foo"));
        Assert.assertTrue(property_server.get_priority() == 0);
    }

    @Test
    public void test_02() {
        PropertyServer property_server = new PropertyServer(
            ModuleServer.allocate(1001), Atom.parse("foo :priority 100"));
        Assert.assertTrue(property_server.get_priority() == 100);
    }

    @Test
    public void test_03() {
        PropertyServer property_server = new PropertyServer(
            ModuleServer.allocate(1001), Atom.parse("foo :priority -6"));
        Assert.assertTrue(property_server.get_priority() == -6);
    }
}
