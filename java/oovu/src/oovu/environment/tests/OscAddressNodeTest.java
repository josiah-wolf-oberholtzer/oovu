package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.Environment;
import oovu.environment.OscAddressNode;
import oovu.nodes.ModuleNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OscAddressNodeTest {
	
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
	}
	
	@Test
	public void test_02() {
		OscAddressNode foo = new OscAddressNode("foo");

		assertArrayEquals(foo.get_parentage(), new OscAddressNode[]{ foo });
		assertEquals(foo.get_child("bar"), null);
		assertEquals(foo.get_parent(), null);
		assertEquals(foo.get_reference_count(), 0);
		assertEquals(foo.get_root(), foo);
		assertEquals(foo.is_empty(), true);
		assertEquals(foo.name, "foo");

		OscAddressNode bar = new OscAddressNode("bar");
		foo.add_child(bar);

		assertArrayEquals(foo.get_parentage(), new OscAddressNode[]{ foo });
		assertEquals(foo.get_child("bar"), bar);
		assertEquals(foo.get_reference_count(), 1);
		assertEquals(foo.get_root(), foo);
		assertEquals(foo.is_empty(), false);

		assertArrayEquals(bar.get_parentage(), new OscAddressNode[]{ bar, foo });
		assertEquals(bar.get_parent(), foo);
		assertEquals(bar.get_reference_count(), 0);
		assertEquals(bar.get_root(), foo);
		assertEquals(bar.is_empty(), true);
		assertEquals(bar.name, "bar");

		foo.remove_child(bar);
		
		assertArrayEquals(foo.get_parentage(), new OscAddressNode[]{ foo });
		assertEquals(foo.get_child("bar"), null);
		assertEquals(foo.get_parent(), null);
		assertEquals(foo.get_reference_count(), 0);
		assertEquals(foo.get_root(), foo);
		assertEquals(foo.is_empty(), true);
		assertEquals(foo.name, "foo");
		
		assertArrayEquals(bar.get_parentage(), new OscAddressNode[]{ bar });
		assertEquals(bar.get_parent(), null);
		assertEquals(bar.get_reference_count(), 0);
		assertEquals(bar.get_root(), bar);
		assertEquals(bar.is_empty(), true);
		assertEquals(bar.name, "bar");
	}
	
	@Test
	public void test_03() {
		OscAddressNode foo = new OscAddressNode("foo");
		assertEquals(foo.is_in_environment(), false);
		Environment.root_osc_address_node.add_child(foo);
		assertEquals(foo.is_in_environment(), true);
		Environment.root_osc_address_node.remove_child(foo);
		assertEquals(foo.is_in_environment(), false);
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
		assertArrayEquals(baz.get_parentage(), new OscAddressNode[]{ baz, bar, foo });
		assertArrayEquals(quux.get_parentage(), new OscAddressNode[]{ quux, bar, foo });
		foo.prune();
		assertArrayEquals(baz.get_parentage(), new OscAddressNode[]{ baz, bar, foo });
		assertArrayEquals(quux.get_parentage(), new OscAddressNode[]{ quux, bar, foo });
		bar.prune();
		assertArrayEquals(baz.get_parentage(), new OscAddressNode[]{ baz, bar, foo });
		assertArrayEquals(quux.get_parentage(), new OscAddressNode[]{ quux, bar, foo });
		baz.prune();
		assertArrayEquals(baz.get_parentage(), new OscAddressNode[]{ baz });
		assertArrayEquals(quux.get_parentage(), new OscAddressNode[]{ quux, bar, foo });
		quux.prune();
	}
	
	@Test
	public void test_05() {
		OscAddressNode foo_osc_address_node = new OscAddressNode("foo");
		OscAddressNode bar_osc_address_node = new OscAddressNode("bar");
		ModuleNode foo_module_node = new ModuleNode(1001, null);
		assertEquals(foo_osc_address_node.is_empty(), true);
		foo_osc_address_node.set_node(foo_module_node);
		assertEquals(foo_osc_address_node.is_empty(), false);
		foo_osc_address_node.add_child(bar_osc_address_node);
		assertEquals(foo_osc_address_node.is_empty(), false);
		foo_osc_address_node.set_node(null);
		assertEquals(foo_osc_address_node.is_empty(), false);
		foo_osc_address_node.remove_child(bar_osc_address_node);
		assertEquals(foo_osc_address_node.is_empty(), true);
	}

}
