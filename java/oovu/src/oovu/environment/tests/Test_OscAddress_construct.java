package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.Environment;
import oovu.environment.OscAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test_OscAddress_construct {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

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
        String address_string = "$#98535-_";
        thrown.expect(RuntimeException.class);
        OscAddress osc_address = new OscAddress(address_string);
	}
	
	@Test
	public void test_construct_02() {
		OscAddress osc_address = new OscAddress("");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_03() {
		OscAddress osc_address = new OscAddress(".");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[0]);
	}

	@Test
	public void test_construct_04() {
		OscAddress osc_address = new OscAddress("..");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ ".." });
	}
	
	@Test
	public void test_construct_06() {
		OscAddress osc_address = new OscAddress("/");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_07() {
		OscAddress osc_address = new OscAddress("./");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_08() {
		OscAddress osc_address = new OscAddress(".//./././//./");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_09() {
		OscAddress osc_address = new OscAddress(":attribute");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, "attribute");
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_10() {
		OscAddress osc_address = new OscAddress("./:attribute");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, "attribute");
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_11() {
		OscAddress osc_address = new OscAddress("/:attribute1/attribute2");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, "attribute1/attribute2");
		assertArrayEquals(osc_address.address_items, new String[0]);
	}
	
	@Test
	public void test_construct_12() {
		OscAddress osc_address = new OscAddress("foo");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ "foo" });
	}
	
	@Test
	public void test_construct_13() {
		OscAddress osc_address = new OscAddress("./foo");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ "foo" });
	}
	
	@Test
	public void test_construct_14() {
		OscAddress osc_address = new OscAddress("../foo");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ "..", "foo" });
	}
	
	@Test
	public void test_construct_15() {
		OscAddress osc_address = new OscAddress("foo/bar/baz");
		assertEquals(osc_address.is_relative, true);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ "foo", "bar", "baz" });
	}
	
	@Test
	public void test_construct_16() {
		OscAddress osc_address = new OscAddress("/foo/bar/baz");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ "foo", "bar", "baz" });
	}
	
	@Test
	public void test_construct_17() {
		OscAddress osc_address = new OscAddress("/foo/../baz");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, null);
		assertArrayEquals(osc_address.address_items, new String[]{ "foo", "..", "baz" });
	}
	
	@Test
	public void test_construct_18() {
		OscAddress osc_address = new OscAddress("/foo/../baz/:quux");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, "quux");
		assertArrayEquals(osc_address.address_items, new String[]{ "foo", "..", "baz" });
	}
	
	@Test
	public void test_construct_19() {
		OscAddress osc_address = new OscAddress("/foo.x/../baz/:quux.1/wux");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, "quux.1/wux");
		assertArrayEquals(osc_address.address_items, new String[]{ "foo.x", "..", "baz" });
	}
	
	@Test
	public void test_construct_20() {
		OscAddress osc_address = new OscAddress("/foo.x/../:quux.1/wux");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, "quux.1/wux");
		assertArrayEquals(osc_address.address_items, new String[]{ "foo.x", ".." });
	}
	
	@Test
	public void test_construct_21() {
		OscAddress osc_address = new OscAddress("/foo.x/./..//:quux.1/wux");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, "quux.1/wux");
		assertArrayEquals(osc_address.address_items, new String[]{ "foo.x", ".." });
	}
	
	@Test
	public void test_construct_22() {
		OscAddress osc_address = new OscAddress("/foo.*/../*.bar/baz/*/:quux.1/wux");
		assertEquals(osc_address.is_relative, false);
		assertEquals(osc_address.node_attribute_name, "quux.1/wux");
		assertArrayEquals(osc_address.address_items, 
			new String[]{ "foo.*", "..", "*.bar", "baz", "*" });
	}

}
