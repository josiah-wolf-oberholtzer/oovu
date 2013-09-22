package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.Environment;
import oovu.environment.OscAddressNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test_OscAddressNode_matches {

	@Before
	public void setUp() throws Exception {
		Environment.reset();
	}

	@After
	public void tearDown() throws Exception {
		Environment.reset();
	}

	@Test
	public void test_matches_01() {
		OscAddressNode foo = new OscAddressNode("foo");
		assertTrue(foo.matches("foo"));
		assertTrue(foo.matches("*"));
		assertFalse(foo.matches("bar"));
		assertFalse(foo.matches("foo.*"));
		assertFalse(foo.matches("*.foo"));
	}
	
	@Test
	public void test_matches_02() {
		OscAddressNode send_one = new OscAddressNode("send.1");
		assertTrue(send_one.matches("*"));
		assertTrue(send_one.matches("send.*"));
	    assertTrue(send_one.matches("*.1"));
	    assertTrue(send_one.matches("*.*"));
	    assertFalse(send_one.matches("send.2"));
	    assertFalse(send_one.matches("*.2"));
	    assertFalse(send_one.matches("foo.1"));
	    assertFalse(send_one.matches("foo.*"));
	}
	
	@Test
	public void test_matches_03() {
		OscAddressNode axis_orbital = new OscAddressNode("axis.orbital");
		assertTrue(axis_orbital.matches("*"));
		assertTrue(axis_orbital.matches("axis.*"));
		assertTrue(axis_orbital.matches("*.orbital"));
		assertTrue(axis_orbital.matches("*.*"));
		assertFalse(axis_orbital.matches("ax.orbital"));
		assertFalse(axis_orbital.matches("is.orbital"));
		assertFalse(axis_orbital.matches("asix.orb"));
	}
}
