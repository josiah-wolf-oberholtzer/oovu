package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test_OscAddressNode_create_address {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	public static final OscAddressNode root = new OscAddressNode("");
	
	@Before
	public void setUp() throws Exception {
		Test_OscAddressNode_create_address.root.clear();
	}

	@After
	public void tearDown() throws Exception {
		Test_OscAddressNode_create_address.root.clear();
	}

	@Test
	public void test_01() {
		OscAddress osc_address = new OscAddress("/foo");
		OscAddressNode foo = root.create_address(osc_address, true);
		assertEquals("foo", foo.get_name());
		assertEquals(foo, root.get_child("foo"));
		assertArrayEquals(
			new String[]{ "/foo" },
			root.get_summary_pieces()
			);
	}
	
	@Test
	public void test_02() {
		OscAddress osc_address = new OscAddress("/foo");
		OscAddressNode original = new OscAddressNode("foo");
		OscAddressNode created = null;
		root.add_child(original);
		created = root.create_address(osc_address, false);
		assertEquals(original, created);
		created = root.create_address(osc_address, true);
		assertNotEquals(created, original);
		assertEquals(created.get_name(), "foo.1");
	}
	
	@Test
	public void test_03() {
		OscAddress osc_address = new OscAddress("/foo/bar");
		OscAddressNode node_1 = root.create_address(osc_address, false);
		assertEquals(node_1.get_osc_address(), "/foo/bar");
		OscAddressNode node_2 = root.create_address(osc_address, false);
		assertEquals(node_1, node_2);
		OscAddressNode node_3 = root.create_address(osc_address, true);
		assertNotEquals(node_3, node_1);
		assertEquals(node_3.get_osc_address(), "/foo/bar.1");
	}

	@Test
	public void test_04() {
		OscAddress osc_address = new OscAddress("/foo/bar/baz");
		OscAddressNode node_1 = root.create_address(osc_address, true);
		assertEquals(node_1.get_osc_address(), "/foo/bar/baz");
		OscAddressNode node_2 = root.create_address(osc_address, true);
		assertEquals(node_2.get_osc_address(), "/foo/bar/baz.1");
		OscAddressNode node_3 = root.create_address(osc_address, true);
		assertEquals(node_3.get_osc_address(), "/foo/bar/baz.2");
		OscAddressNode node_4 = root.create_address(osc_address, true);
		assertEquals(node_4.get_osc_address(), "/foo/bar/baz.3");
		assertArrayEquals(root.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/bar",
				"/foo/bar/baz",
				"/foo/bar/baz.1",
				"/foo/bar/baz.2",
				"/foo/bar/baz.3"
			});
	}
	
	@Test
	public void test_05() {
		OscAddress osc_address_one = new OscAddress("/foo/bar");
		OscAddress osc_address_two = new OscAddress("baz/quux");
		OscAddressNode foo_bar = root.create_address(osc_address_one, false);
		assertEquals(foo_bar.get_osc_address(), "/foo/bar");
		OscAddressNode foo_bar_baz_quux = foo_bar.create_address(osc_address_two, false);
		assertEquals(foo_bar_baz_quux.get_osc_address(), "/foo/bar/baz/quux");
		OscAddressNode baz_quux = root.create_address(osc_address_two, false);
		assertEquals(baz_quux.get_osc_address(), "/baz/quux");
	}
	
	@Test
	public void test_06() {
		OscAddress osc_address = new OscAddress("/foo/*/baz");
        thrown.expect(RuntimeException.class);
        OscAddressNode created = root.create_address(osc_address, true);
        assertNull(created);
	}

	@Test
	public void test_07() {
		OscAddress osc_address = new OscAddress("/foo/../baz");
        thrown.expect(RuntimeException.class);
        OscAddressNode created = root.create_address(osc_address, true);
        assertNull(created);
	}
}
