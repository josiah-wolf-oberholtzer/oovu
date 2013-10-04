package oovu.servers.tests;

import java.util.List;

import oovu.Method;
import oovu.Module;
import oovu.Property;
import oovu.addressing.Environment;
import oovu.servers.AttributeServer;
import oovu.servers.ModuleServer;
import oovu.servers.members.PropertyServer;

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
        Module module = new Module(Atom.parse("1001 foo"));
        Property property_one = new Property(Atom.parse("1001 bar"));
        Property property_two = new Property(Atom.parse("1001 baz"));
        Property property_three = new Property(Atom.parse("1001 quux"));
        Property property_four = new Property(Atom.parse("1001 wux"));
        Method method_one = new Method(Atom.parse("1001 woo"));
        ModuleServer module_server = (ModuleServer) module.get_server();
        List<PropertyServer> properties = module_server
            .get_child_property_servers();
        String[] result = new String[properties.size()];
        for (int i = 0, j = properties.size(); i < j; i++) {
            result[i] = properties.get(i).get_osc_address_string() + " "
                + properties.get(i).get_priority();
        }
        Assert.assertArrayEquals(new String[] {
            "/foo/bar 0", "/foo/baz 0", "/foo/quux 0", "/foo/wux 0"
        }, result);
    }

    @Test
    public void test_02() {
        Module module = new Module(Atom.parse("1001 foo"));
        Property property_one = new Property(Atom.parse("1001 bar :priority 1"));
        Property property_two = new Property(Atom.parse("1001 baz :priority 2"));
        Property property_three = new Property(
            Atom.parse("1001 quux :priority 3"));
        Property property_four = new Property(
            Atom.parse("1001 wux :priority 4"));
        Method method_one = new Method(Atom.parse("1001 woo"));
        AttributeServer property_server_four = (AttributeServer) property_four
            .get_server();
        ModuleServer module_server = (ModuleServer) module.get_server();
        List<PropertyServer> properties = module_server
            .get_child_property_servers();
        String[] result = new String[properties.size()];
        for (int i = 0, j = properties.size(); i < j; i++) {
            result[i] = properties.get(i).get_osc_address_string() + " "
                + properties.get(i).get_priority();
        }
        Assert.assertArrayEquals(new String[] {
            "/foo/wux 4", "/foo/quux 3", "/foo/baz 2", "/foo/bar 1"
        }, result);
    }
}
