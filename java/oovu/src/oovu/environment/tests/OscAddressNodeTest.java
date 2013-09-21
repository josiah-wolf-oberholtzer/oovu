package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.Environment;
import oovu.environment.OscAddressNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OscAddressNodeTest {

	public void create_osc_address_nodes() {
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
	
	@Before
	public void setUp() throws Exception {
		Environment.reset();
	}

	@After
	public void tearDown() throws Exception {
		Environment.reset();
	}
	
	@Test
	public void test_create_nodes_01() {
		this.create_osc_address_nodes();
	}
	
	@Test
	public void test_create_nodes_02() {
		OscAddressNode foo = new OscAddressNode("foo");

		assertArrayEquals(foo.get_parentage(), new OscAddressNode[]{ foo });
		assertEquals(foo.get_child("bar"), null);
		assertEquals(foo.get_parent(), null);
		assertEquals(foo.get_reference_count(), 0);
		assertEquals(foo.get_root(), foo);
		assertEquals(foo.is_empty(), true);

		OscAddressNode bar = new OscAddressNode("bar");
		foo.add_child(bar);

		assertArrayEquals(foo.get_parentage(), new OscAddressNode[]{ foo });
		assertEquals(foo.get_child("bar"), bar);
		assertEquals(foo.get_reference_count(), 1);
		assertEquals(foo.get_root(), foo);
		assertEquals(foo.is_empty(), false);

		assertArrayEquals(bar.get_parentage(), new OscAddressNode[]{ bar, foo });
		assertEquals(bar.get_parent(), foo);
		assertEquals(bar.get_root(), foo);
	}

}
