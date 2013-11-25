package oovu.servers.tests;

import oovu.addresses.Environment;
import oovu.servers.ModuleServer;
import oovu.servers.PropertyServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__ModuleServer__get_child_property_servers {
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
        ModuleServer module = ModuleServer.allocate(1001);
        module.acquire_name("foo");
        PropertyServer property_aaa =
            PropertyServer.allocate(1001, "aaa", Atom.parse(""));
        PropertyServer property_bbb =
            PropertyServer.allocate(1001, "bbb", Atom.parse(""));
        PropertyServer property_ccc =
            PropertyServer.allocate(1001, "ccc", Atom.parse(""));
        Assert.assertArrayEquals(new PropertyServer[] {
            property_aaa, property_bbb, property_ccc
        }, module.get_child_property_servers().toArray());
    }

    @Test
    public void test_02() {
        ModuleServer module = ModuleServer.allocate(1001);
        module.acquire_name("foo");
        PropertyServer property_aaa =
            PropertyServer.allocate(1001, "aaa", Atom.parse(":priority 1"));
        PropertyServer property_bbb =
            PropertyServer.allocate(1001, "bbb", Atom.parse(":priority 2"));
        PropertyServer property_ccc =
            PropertyServer.allocate(1001, "ccc", Atom.parse(":priority 2"));
        Assert.assertArrayEquals(new PropertyServer[] {
            property_bbb, property_ccc, property_aaa
        }, module.get_child_property_servers().toArray());
    }

    @Test
    public void test_03() {
        ModuleServer module = ModuleServer.allocate(1001);
        module.acquire_name("foo");
        PropertyServer property_aaa =
            PropertyServer.allocate(1001, "aaa", Atom.parse(":priority 1"));
        PropertyServer property_bbb =
            PropertyServer.allocate(1001, "bbb", Atom.parse(":priority 2"));
        PropertyServer property_ccc =
            PropertyServer.allocate(1001, "ccc", Atom.parse(":priority 1"));
        Assert.assertArrayEquals(new PropertyServer[] {
            property_bbb, property_aaa, property_ccc
        }, module.get_child_property_servers().toArray());
    }
}
