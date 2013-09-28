package oovu.tests;

import oovu.Binding;
import oovu.Module;
import oovu.Property;
import oovu.addressing.Environment;
import oovu.addressing.OscAddressNode;
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
        Assert.assertEquals("/foo", module_server.get_osc_address());
        Assert.assertEquals(1, module_server.get_reference_count());
        Assert.assertTrue(module_server.server_clients.contains(module_client));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "    <Node 'foo':1001 (server: ModuleServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        module_client.detach_from_server();
        module_server.deallocate_if_necessary();
        Assert.assertEquals(0, module_server.get_reference_count());
        Assert.assertNull(module_server.get_osc_address_node());
        Assert.assertNull(osc_address_node.get_parent());
    }

    @Test
    public void test_02() {
        Binding binding = new Binding(Atom.newAtom(new String[] {
            "/foo/bar"
        }));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>", "    <Node 'foo':null>",
            "        <Node 'bar':null (bindings: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Property property = new Property(Atom.parse("1001 bar"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "    <Node null:1001 (server: ModuleServer)>",
            "        <Node 'bar':null (server: PropertyServer)>",
            "    <Node 'foo':null>", "        <Node 'bar':null (bindings: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        Module module = new Module(Atom.parse("1001 foo"));
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "    <Node 'foo':1001 (server: ModuleServer)>",
            "        <Node 'bar':null (bindings: 1, server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        module.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>",
            "    <Node 'foo':1001 (server: ModuleServer)>",
            "        <Node 'bar':null (bindings: 1, server: PropertyServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        property.detach_from_server();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>", "    <Node 'foo':null>",
            "        <Node 'bar':null (bindings: 1)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
        binding.detach();
        Assert.assertArrayEquals(new String[] {
            "<Node '':null (server: RootServer)>"
        }, Environment.root_osc_address_node.get_debug_pieces());
    }
}
