package oovu.environment.tests;

import static org.junit.Assert.assertArrayEquals;
import oovu.environment.OscAddress;
import oovu.environment.OscAddressNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test__OscAddressNode__prune {

	public static final OscAddressNode root = new OscAddressNode("");
	
	@Before
	public void setUp() throws Exception {
		root.clear();
	}

	@After
	public void tearDown() throws Exception {
		root.clear();
	}

	@Test
	public void test_01() {
		OscAddress osc_address = OscAddress.from_cache("/foo/bar/baz/quux");
		OscAddressNode node = root.create_address(osc_address, false);
		assertArrayEquals(
			root.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/bar",
				"/foo/bar/baz",
				"/foo/bar/baz/quux"
			});
		node.prune();
		assertArrayEquals(
			root.get_summary_pieces(),
			new String[]{}
			);
	}


	@Test
	public void test_02() {
		OscAddress osc_address = OscAddress.from_cache("/foo/bar/baz/quux");
		OscAddressNode node = root.create_address(osc_address, false);
		assertArrayEquals(
			root.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/bar",
				"/foo/bar/baz",
				"/foo/bar/baz/quux"
			});
		node.get_parent().prune();
		assertArrayEquals(
			root.get_summary_pieces(),
			new String[]{
				"/foo",
				"/foo/bar",
				"/foo/bar/baz",
				"/foo/bar/baz/quux"
			});
	}
}
