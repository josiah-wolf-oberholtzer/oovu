package oovu.servers.tests;

import java.util.Set;

import oovu.addressing.Environment;
import oovu.addressing.OscAddress;
import oovu.addressing.OscAddressNode;
import oovu.servers.ModuleServer;
import oovu.servers.members.PropertyServer;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__servers__lifecycle {

    @Before
    public void setUp() throws Exception {
        Environment.reset();
    }

    @After
    public void tearDown() throws Exception {
        Environment.reset();
    }

    @Test
    public void test_construct_01() {
        Assert.assertEquals(Environment.root_server.get_osc_address_node(),
            Environment.root_osc_address_node);
        Assert.assertEquals(Environment.root_osc_address_node.get_server(),
            Environment.root_server);
        Environment.reset();
        Assert.assertEquals(Environment.root_server.get_osc_address_node(),
            Environment.root_osc_address_node);
        Assert.assertEquals(Environment.root_osc_address_node.get_server(),
            Environment.root_server);
        Environment.root_server.deallocate_if_necessary();
        Assert.assertEquals(Environment.root_server.get_osc_address_node(),
            Environment.root_osc_address_node);
        Assert.assertEquals(Environment.root_osc_address_node.get_server(),
            Environment.root_server);
        Environment.root_osc_address_node.prune();
        Assert.assertEquals(Environment.root_server.get_osc_address_node(),
            Environment.root_osc_address_node);
        Assert.assertEquals(Environment.root_osc_address_node.get_server(),
            Environment.root_server);
    }

    @Test
    public void test_construct_02() {
        OscAddress osc_address = null;
        OscAddressNode osc_address_node = null;
        ModuleServer foo_module_server = ModuleServer.allocate(1001);
        foo_module_server.acquire_name("foo");
        PropertyServer foo_bar_property_server = PropertyServer.allocate(1001,
            "bar", new Atom[0]);
        PropertyServer foo_baz_quux_property_server = PropertyServer.allocate(
            1001, "baz/quux", new Atom[0]);
        Assert.assertArrayEquals(
            Environment.root_osc_address_node.get_summary_pieces(),
            new String[] {
                "/foo", "/foo/bar", "/foo/baz", "/foo/baz/quux"
            });

        osc_address = OscAddress.from_cache("/foo");
        osc_address_node = Environment.root_osc_address_node.search_for_one(osc_address);
        Assert.assertEquals(osc_address_node.get_server(), foo_module_server);
        Assert.assertEquals(foo_module_server.get_osc_address_node(), osc_address_node);

        osc_address = OscAddress.from_cache("/foo/bar");
        osc_address_node = Environment.root_osc_address_node.search_for_one(osc_address);
        Assert.assertEquals(osc_address_node.get_server(), foo_bar_property_server);
        Assert.assertEquals(foo_bar_property_server.get_osc_address_node(), osc_address_node);

        osc_address = OscAddress.from_cache("/foo/baz");
        osc_address_node = Environment.root_osc_address_node.search_for_one(osc_address);
        Assert.assertEquals(osc_address_node.get_server(), null);

        osc_address = OscAddress.from_cache("/foo/baz/quux");
        osc_address_node = Environment.root_osc_address_node.search_for_one(osc_address);
        Assert.assertEquals(osc_address_node.get_server(), foo_baz_quux_property_server);
        Assert.assertEquals(foo_baz_quux_property_server.get_osc_address_node(), osc_address_node);

        Assert.assertEquals(foo_module_server.get_osc_address(), "/foo");
        Assert.assertEquals(foo_bar_property_server.get_osc_address(), "/foo/bar");
        Assert.assertEquals(foo_baz_quux_property_server.get_osc_address(), "/foo/baz/quux");
        
        foo_bar_property_server.deallocate_if_necessary();
        Assert.assertArrayEquals(
            Environment.root_osc_address_node.get_summary_pieces(),
            new String[] {
                "/foo", "/foo/baz", "/foo/baz/quux"
            });

        foo_baz_quux_property_server.deallocate_if_necessary();
        Assert.assertArrayEquals(
            Environment.root_osc_address_node.get_summary_pieces(),
            new String[] {});
    }
}
