package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.Environment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test_Environment_reset {

	@Before
	public void setUp() throws Exception {
		Environment.reset();
	}

	@After
	public void tearDown() throws Exception {
		Environment.reset();
	}
	
	@Test
	public void test_reset() {
		//assertEquals(Environment.osc_addresses.size(), 0);
		assertEquals(Environment.modules_by_module_id.size(), 0);
		assertEquals(Environment.pull_addresses.size(), 0);
		assertEquals(Environment.push_addresses.size(), 0);
		assertEquals(Environment.root_node.get_reference_count(), 0);
		assertEquals(Environment.root_osc_address_node.get_reference_count(), 0);
	}

}
