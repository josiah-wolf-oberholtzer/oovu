package oovu.servers.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import oovu.environment.Environment;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;
import oovu.servers.ModuleServer;
import oovu.servers.PropertyServer;

import org.junit.After;
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
		assertEquals(
			Environment.root_node.get_osc_address_node(),
			Environment.root_osc_address_node
			);
		assertEquals(
			Environment.root_osc_address_node.get_node(),
			Environment.root_node
			);
		Environment.reset();
		assertEquals(
			Environment.root_node.get_osc_address_node(),
			Environment.root_osc_address_node
			);
		assertEquals(
			Environment.root_osc_address_node.get_node(),
			Environment.root_node
			);
		Environment.root_node.deallocate_if_necessary();
		assertEquals(
			Environment.root_node.get_osc_address_node(),
			Environment.root_osc_address_node
			);
		assertEquals(
			Environment.root_osc_address_node.get_node(),
			Environment.root_node
			);
		Environment.root_osc_address_node.prune();
		assertEquals(
			Environment.root_node.get_osc_address_node(),
			Environment.root_osc_address_node
			);
		assertEquals(
			Environment.root_osc_address_node.get_node(),
			Environment.root_node
			);
	}

	@Test
	public void test_construct_02() {
		OscAddress osc_address = null;
		Set<OscAddressNode> search_result = null;
		ModuleServer foo_module_node = ModuleServer.allocate(1001);
		foo_module_node.register_name("foo");
		PropertyServer foo_bar_property_node = PropertyServer.allocate(
			1001, "bar", new Atom[0]);
		PropertyServer foo_baz_quux_property_node = PropertyServer.allocate(
			1001, "baz/quux", new Atom[0]);
		assertArrayEquals(
			Environment.root_osc_address_node.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/bar",
				"/foo/baz",
				"/foo/baz/quux"
			});
		osc_address = OscAddress.from_cache("/foo");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			foo_module_node
			);
		osc_address = OscAddress.from_cache("/foo/bar");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			foo_bar_property_node
			);
		osc_address = OscAddress.from_cache("/foo/baz");
		search_result = Environment.root_osc_address_node.search(osc_address);
		assertEquals(
			search_result.toArray(new OscAddressNode[0])[0].get_node(),
			null
			);
		osc_address = OscAddress.from_cache("/foo/baz/quux");
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
