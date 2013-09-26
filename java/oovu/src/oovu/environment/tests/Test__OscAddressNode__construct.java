package oovu.environment.tests;

import oovu.environment.Environment;
import oovu.environment.OscAddressNode;
import oovu.servers.ModuleServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test__OscAddressNode__construct {

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
        OscAddressNode root = Environment.root_osc_address_node;
        OscAddressNode module_one = new OscAddressNode("module.one");
        OscAddressNode module_one_dsp = new OscAddressNode("dsp");
        OscAddressNode module_one_dsp_active = new OscAddressNode("active");
        OscAddressNode module_one_dsp_gain = new OscAddressNode("gain");
        OscAddressNode module_two = new OscAddressNode("module.two");
        OscAddressNode module_two_dsp = new OscAddressNode("dsp");
        OscAddressNode module_two_dsp_active = new OscAddressNode("active");
        OscAddressNode module_two_dsp_gain = new OscAddressNode("gain");
        root.add_child(module_one);
        root.add_child(module_two);
        module_one.add_child(module_one_dsp);
        module_two.add_child(module_two_dsp);
        module_one_dsp.add_child(module_one_dsp_active);
        module_one_dsp.add_child(module_one_dsp_gain);
        module_two_dsp.add_child(module_two_dsp_active);
        module_two_dsp.add_child(module_two_dsp_gain);
        Assert.assertEquals(root.get_reference_count(), 2);
        Environment.reset();
        Assert.assertEquals(root.get_reference_count(), 0);
    }

    @Test
    public void test_02() {
        OscAddressNode foo = new OscAddressNode("foo");
        Assert.assertArrayEquals(foo.get_parentage(), new OscAddressNode[] {
            foo
        });
        Assert.assertEquals(foo.get_named_child("bar"), null);
        Assert.assertEquals(foo.get_parent(), null);
        Assert.assertEquals(foo.get_reference_count(), 0);
        Assert.assertEquals(foo.get_root(), foo);
        Assert.assertEquals(foo.is_empty(), true);
        Assert.assertEquals(foo.get_name(), "foo");
        OscAddressNode bar = new OscAddressNode("bar");
        foo.add_child(bar);
        Assert.assertArrayEquals(foo.get_parentage(), new OscAddressNode[] {
            foo
        });
        Assert.assertEquals(foo.get_named_child("bar"), bar);
        Assert.assertEquals(foo.get_reference_count(), 1);
        Assert.assertEquals(foo.get_root(), foo);
        Assert.assertEquals(foo.is_empty(), false);
        Assert.assertArrayEquals(bar.get_parentage(), new OscAddressNode[] {
            bar, foo
        });
        Assert.assertEquals(bar.get_parent(), foo);
        Assert.assertEquals(bar.get_reference_count(), 0);
        Assert.assertEquals(bar.get_root(), foo);
        Assert.assertEquals(bar.is_empty(), true);
        Assert.assertEquals(bar.get_name(), "bar");
        foo.remove_child(bar);
        Assert.assertArrayEquals(foo.get_parentage(), new OscAddressNode[] {
            foo
        });
        Assert.assertEquals(foo.get_named_child("bar"), null);
        Assert.assertEquals(foo.get_parent(), null);
        Assert.assertEquals(foo.get_reference_count(), 0);
        Assert.assertEquals(foo.get_root(), foo);
        Assert.assertEquals(foo.is_empty(), true);
        Assert.assertEquals(foo.get_name(), "foo");
        Assert.assertArrayEquals(bar.get_parentage(), new OscAddressNode[] {
            bar
        });
        Assert.assertEquals(bar.get_parent(), null);
        Assert.assertEquals(bar.get_reference_count(), 0);
        Assert.assertEquals(bar.get_root(), bar);
        Assert.assertEquals(bar.is_empty(), true);
        Assert.assertEquals(bar.get_name(), "bar");
    }

    @Test
    public void test_03() {
        OscAddressNode foo = new OscAddressNode("foo");
        Assert.assertEquals(foo.is_in_environment(), false);
        Environment.root_osc_address_node.add_child(foo);
        Assert.assertEquals(foo.is_in_environment(), true);
        Environment.root_osc_address_node.remove_child(foo);
        Assert.assertEquals(foo.is_in_environment(), false);
    }

    @Test
    public void test_04() {
        OscAddressNode foo = new OscAddressNode("foo");
        OscAddressNode bar = new OscAddressNode("bar");
        OscAddressNode baz = new OscAddressNode("baz");
        OscAddressNode quux = new OscAddressNode("quux");
        foo.add_child(bar);
        bar.add_child(baz);
        bar.add_child(quux);
        Assert.assertArrayEquals(baz.get_parentage(), new OscAddressNode[] {
            baz, bar, foo
        });
        Assert.assertArrayEquals(quux.get_parentage(), new OscAddressNode[] {
            quux, bar, foo
        });
        foo.prune();
        Assert.assertArrayEquals(baz.get_parentage(), new OscAddressNode[] {
            baz, bar, foo
        });
        Assert.assertArrayEquals(quux.get_parentage(), new OscAddressNode[] {
            quux, bar, foo
        });
        bar.prune();
        Assert.assertArrayEquals(baz.get_parentage(), new OscAddressNode[] {
            baz, bar, foo
        });
        Assert.assertArrayEquals(quux.get_parentage(), new OscAddressNode[] {
            quux, bar, foo
        });
        baz.prune();
        Assert.assertArrayEquals(baz.get_parentage(), new OscAddressNode[] {
            baz
        });
        Assert.assertArrayEquals(quux.get_parentage(), new OscAddressNode[] {
            quux, bar, foo
        });
        quux.prune();
    }

    @Test
    public void test_05() {
        OscAddressNode foo_osc_address_node = new OscAddressNode("foo");
        OscAddressNode bar_osc_address_node = new OscAddressNode("bar");
        ModuleServer foo_module_node = new ModuleServer(1001, null);
        Assert.assertEquals(foo_osc_address_node.is_empty(), true);
        foo_osc_address_node.set_server(foo_module_node);
        Assert.assertEquals(foo_osc_address_node.is_empty(), false);
        foo_osc_address_node.add_child(bar_osc_address_node);
        Assert.assertEquals(foo_osc_address_node.is_empty(), false);
        foo_osc_address_node.set_server(null);
        Assert.assertEquals(foo_osc_address_node.is_empty(), false);
        foo_osc_address_node.remove_child(bar_osc_address_node);
        Assert.assertEquals(foo_osc_address_node.is_empty(), true);
    }

    @Test
    public void test_06() {
        OscAddressNode foo = new OscAddressNode("foo");
        OscAddressNode bar = new OscAddressNode("bar");
        OscAddressNode baz = new OscAddressNode("baz");
        foo.add_child(bar);
        Assert.assertEquals(bar.get_parent(), foo);
        Assert.assertEquals(foo.get_named_child("bar"), bar);
        baz.add_child(bar);
        Assert.assertEquals(bar.get_parent(), baz);
        Assert.assertEquals(foo.get_named_child("bar"), null);
        Assert.assertEquals(baz.get_named_child("bar"), bar);
        Assert.assertArrayEquals(bar.get_parentage(), new OscAddressNode[] {
            bar, baz
        });
        bar.add_child(baz);
        Assert.assertArrayEquals(bar.get_parentage(), new OscAddressNode[] {
            bar, baz
        });
    }
}
