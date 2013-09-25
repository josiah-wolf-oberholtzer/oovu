package oovu.servers.tests;

import java.util.Set;

import oovu.environment.Environment;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;
import oovu.servers.ModuleServer;
import oovu.servers.members.PropertyServer;

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
        Set<OscAddressNode> search_result = null;
        ModuleServer foo_module_server = ModuleServer.allocate(1001);
        foo_module_server.register_name("foo");
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
        search_result = Environment.root_osc_address_node.search(osc_address);
        Assert.assertEquals(
            search_result.toArray(new OscAddressNode[0])[0].get_server(),
            foo_module_server);
        osc_address = OscAddress.from_cache("/foo/bar");
        search_result = Environment.root_osc_address_node.search(osc_address);
        Assert.assertEquals(
            search_result.toArray(new OscAddressNode[0])[0].get_server(),
            foo_bar_property_server);
        osc_address = OscAddress.from_cache("/foo/baz");
        search_result = Environment.root_osc_address_node.search(osc_address);
        Assert.assertEquals(
            search_result.toArray(new OscAddressNode[0])[0].get_server(), null);
        osc_address = OscAddress.from_cache("/foo/baz/quux");
        search_result = Environment.root_osc_address_node.search(osc_address);
        Assert.assertEquals(
            search_result.toArray(new OscAddressNode[0])[0].get_server(),
            foo_baz_quux_property_server);
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
