package oovu.nodes.tests;

import static org.junit.Assert.*;

import java.util.Set;

import oovu.environment.Environment;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;
import oovu.nodes.ModuleNode;
import oovu.nodes.PropertyNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__node__lifecycle {

	@Before
	public void setUp() throws Exception {
		Environment.reset();
	}

	@After
	public void tearDown() throws Exception {
		Environment.reset();
	}

	@Test
	public void test_construct() {
		OscAddress osc_address = null;
		Set<OscAddressNode> search_result = null;
		ModuleNode foo_module_node = ModuleNode.allocate(1001);
		foo_module_node.register_name("foo");
		PropertyNode foo_bar_property_node = PropertyNode.allocate(
			1001, "bar", new Atom[0]);
		PropertyNode foo_baz_quux_property_node = PropertyNode.allocate(
			1001, "baz/quux", new Atom[0]);
		assertArrayEquals(
			Environment.root_osc_address_node.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/bar",
				"/foo/baz",
				"/foo/baz/quux"
			});
		osc_address = new OscAddress("/foo");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			foo_module_node
			);
		osc_address = new OscAddress("/foo/bar");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			foo_bar_property_node
			);
		osc_address = new OscAddress("/foo/baz");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			null
			);
		osc_address = new OscAddress("/foo/baz/quux");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			foo_baz_quux_property_node
			);
		foo_bar_property_node.deallocate_if_necessary();
		assertArrayEquals(
			Environment.root_osc_address_node.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/baz",
				"/foo/baz/quux"
			});
		foo_baz_quux_property_node.deallocate_if_necessary();
		assertArrayEquals(
			Environment.root_osc_address_node.get_summary_pieces(),
			new String[]{ });
	}

}
