package oovu.tests;

import oovu.Module;
import oovu.Property;
import oovu.Proxy;
import oovu.addresses.Environment;
import oovu.addresses.OscAddressNode;
import oovu.servers.Server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__OOVU__lifecycle {

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
        Atom[] atoms = new Atom[2];
        atoms[0] = Atom.newAtom(1001);
        atoms[1] = Atom.newAtom("foo");
        Module module_client = new Module(atoms);
        Server module_server = module_client.get_server();
        Assert.assertNotNull(module_server);
        OscAddressNode osc_address_node = module_server.get_osc_address_node();
        Assert.assertNotNull(osc_address_node);
        Assert.assertEquals("foo", osc_address_node.get_name());
        Assert.assertEquals(1001, osc_address_node.get_number());
        Assert.assertEquals(osc_address_node.get_parent(),
            Environment.root_osc_address_node);
        Assert.assertEquals("foo", module_server.get_name());
        Assert.assertEquals("/foo", module_server.get_osc_address_string());
        Assert.assertEquals(1, module_server.get_reference_count());
        Assert.assertTrue(module_server.server_clients.contains(module_client));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        module_client.detach_from_server();
        module_server.deallocate_if_necessary();
        Assert.assertEquals(0, module_server.get_reference_count());
        Assert.assertNull(module_server.get_osc_address_node());
        Assert.assertNull(osc_address_node.get_parent());
    }

    @Test
    public void test_02() {
        Proxy proxy = new Proxy(Atom.parse("/foo/bar"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':null>",
            "........<Node 'bar':null (proxies: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Property property = new Property(Atom.parse("1001 bar"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node null:1001 (server: ModuleServer)>",
            "........<Node 'bar':null (server: PropertyServer)>",
            "....<Node 'foo':null>",
            "........<Node 'bar':null (proxies: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Module module = new Module(Atom.parse("1001 foo"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>",
            "........<Node 'bar':null (proxies: 1, server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        module.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>",
            "........<Node 'bar':null (proxies: 1, server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        property.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':null>",
            "........<Node 'bar':null (proxies: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        proxy.detach();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
    }

    @Test
    public void test_03() {
        Module module_1001 = new Module(Atom.parse("1001 foo"));
        Property property_1001 = new Property(Atom.parse("1001 bar"));
        Proxy proxy = new Proxy(Atom.parse("/foo/baz"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>",
            "........<Node 'bar':null (server: PropertyServer)>",
            "........<Node 'baz':null (proxies: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        proxy.detach();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>",
            "........<Node 'bar':null (server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        property_1001.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        module_1001.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
        }, Environment.root_osc_address_node.get_debug_pieces());
    }

    @Test
    public void test_04() {
        Property property_1001 = new Property(Atom.parse("1001 baz"));
        Property property_2002 = new Property(Atom.parse("2002 quux"));
        Module module_1001 = new Module(Atom.parse("1001 foo"));
        Module module_2002 = new Module(Atom.parse("2002 foo"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>",
            "........<Node 'baz':null (server: PropertyServer)>",
            "....<Node 'foo.1':2002 (server: ModuleServer)>",
            "........<Node 'quux':null (server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Proxy proxy = new Proxy(Atom.parse("/foo/bar"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':1001 (server: ModuleServer)>",
            "........<Node 'bar':null (proxies: 1)>",
            "........<Node 'baz':null (server: PropertyServer)>",
            "....<Node 'foo.1':2002 (server: ModuleServer)>",
            "........<Node 'quux':null (server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        property_1001.detach_from_server();
        module_1001.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':null>",
            "........<Node 'bar':null (proxies: 1)>",
            "....<Node 'foo.1':2002 (server: ModuleServer)>",
            "........<Node 'quux':null (server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Property property_3003 = new Property(Atom.parse("3003 baz"));
        Module module_3003 = new Module(Atom.parse("3003 foo"));
        module_2002.detach_from_server();
        property_2002.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':3003 (server: ModuleServer)>",
            "........<Node 'bar':null (proxies: 1)>",
            "........<Node 'baz':null (server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        proxy.detach();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "....<Node 'foo':3003 (server: ModuleServer)>",
            "........<Node 'baz':null (server: PropertyServer)>",
        }, Environment.root_osc_address_node.get_debug_pieces());
        property_3003.detach_from_server();
        module_3003.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
    }

    @Test
    public void test_05() {
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Module module_one = new Module(Atom.parse("1001 input~"));
        Module module_two = new Module(Atom.parse("2002 input~"));
        Module module_three = new Module(Atom.parse("3003 input~"));
        Property property_one = new Property(Atom.parse("1001 source"));
        Property property_two = new Property(Atom.parse("2002 source"));
        Property property_three = new Property(Atom.parse("3003 source"));
        Proxy proxy = new Proxy(Atom.parse("/input~.1/source"));
        Assert
            .assertArrayEquals(
                new String[] {
                    "<Node '':null (server: RootServer)>",
                    "....<Node 'input~':1001 (server: ModuleServer)>",
                    "........<Node 'source':null (server: PropertyServer)>",
                    "....<Node 'input~.1':2002 (server: ModuleServer)>",
                    "........<Node 'source':null (proxies: 1, server: PropertyServer)>",
                    "....<Node 'input~.2':3003 (server: ModuleServer)>",
                    "........<Node 'source':null (server: PropertyServer)>"
                }, Environment.root_osc_address_node.get_debug_pieces());
        proxy.notifyDeleted();
        property_one.notifyDeleted();
        property_two.notifyDeleted();
        property_three.notifyDeleted();
        module_one.notifyDeleted();
        module_two.notifyDeleted();
        module_three.notifyDeleted();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
    }

    @Test
    public void test_06() {
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Proxy proxy = new Proxy(Atom.parse("/input~.1/source"));
        Module module_one = new Module(Atom.parse("1001 input~"));
        Module module_two = new Module(Atom.parse("2002 input~"));
        Module module_three = new Module(Atom.parse("3003 input~"));
        Property property_one = new Property(Atom.parse("1001 source"));
        Property property_two = new Property(Atom.parse("2002 source"));
        Property property_three = new Property(Atom.parse("3003 source"));
        Assert
            .assertArrayEquals(
                new String[] {
                    "<Node '':null (server: RootServer)>",
                    "....<Node 'input~':1001 (server: ModuleServer)>",
                    "........<Node 'source':null (server: PropertyServer)>",
                    "....<Node 'input~.1':2002 (server: ModuleServer)>",
                    "........<Node 'source':null (proxies: 1, server: PropertyServer)>",
                    "....<Node 'input~.2':3003 (server: ModuleServer)>",
                    "........<Node 'source':null (server: PropertyServer)>"
                }, Environment.root_osc_address_node.get_debug_pieces());
        proxy.notifyDeleted();
        property_one.notifyDeleted();
        property_two.notifyDeleted();
        property_three.notifyDeleted();
        module_one.notifyDeleted();
        module_two.notifyDeleted();
        module_three.notifyDeleted();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
    }
}
