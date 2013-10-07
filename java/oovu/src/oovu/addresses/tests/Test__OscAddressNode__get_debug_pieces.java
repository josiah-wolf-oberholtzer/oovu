package oovu.addresses.tests;

import java.util.HashMap;

import oovu.Proxy;
import oovu.addresses.Environment;
import oovu.addresses.OscAddressNode;
import oovu.servers.ModuleServer;
import oovu.servers.members.PropertyServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__OscAddressNode__get_debug_pieces {

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
        OscAddressNode root = new OscAddressNode("");
        OscAddressNode n1001 = new OscAddressNode(1001);
        OscAddressNode foo = new OscAddressNode("foo");
        OscAddressNode foo_bar = new OscAddressNode("bar");
        OscAddressNode n1001_bar = new OscAddressNode("bar");
        root.add_child(foo);
        root.add_child(n1001);
        foo.add_child(foo_bar);
        n1001.add_child(n1001_bar);
        foo_bar.add_proxy(new Proxy(Atom.parse("")));
        ModuleServer n1001_module_server = new ModuleServer(1001,
            new HashMap<String, Atom[]>());
        n1001_module_server.attach_to_osc_address_node(n1001);
        PropertyServer n1001_bar_property_server = new PropertyServer(
            n1001_module_server, new HashMap<String, Atom[]>());
        n1001_bar_property_server.attach_to_osc_address_node(n1001_bar);
        String[] debug_pieces = root.get_debug_pieces();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null>",
            "....<Node null:1001 (server: ModuleServer)>",
            "........<Node 'bar':null (server: PropertyServer)>",
            "....<Node 'foo':null>",
            "........<Node 'bar':null (proxies: 1)>"
        }, debug_pieces);
    }
}
