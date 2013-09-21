package oovu.environment.tests;

import static org.junit.Assert.*;
import oovu.environment.Environment;
import oovu.environment.OscAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OscAddressTest {

	@Before
	public void setUp() throws Exception {
		Environment.reset();
	}

	@After
	public void tearDown() throws Exception {
		Environment.reset();
	}
	
	@Test
	public void test_all_are_valid_names_01() {
		String[] names = new String[]{ "aaa", "bbb", "ccc" };
		boolean result = OscAddress.all_are_valid_names(names);
		assertTrue(result);
	}

	@Test
	public void test_all_are_valid_names_02() {
		String[] names = new String[]{ "one1one", "two2two", "three3three" };
		boolean result = OscAddress.all_are_valid_names(names);
		assertTrue(result);
	}

	@Test
	public void test_all_are_valid_names_03() {
		String[] names = new String[]{ "axis.x", "axis.y" };
		boolean result = OscAddress.all_are_valid_names(names);
		assertTrue(result);
	}

	@Test
	public void test_all_are_valid_names_04() {
		String[] names = new String[]{ "send.1", "send.2" };
		boolean result = OscAddress.all_are_valid_names(names);
		assertTrue(result);
	}

	@Test
	public void test_all_are_valid_names_05() {
		String[] names = new String[]{ "1", "2", "3" };
		boolean result = OscAddress.all_are_valid_names(names);
		assertFalse(result);
	}

	@Test
	public void test_all_are_valid_names_06() {
		String[] names = new String[]{ "foo/bar" };
		boolean result = OscAddress.all_are_valid_names(names);
		assertFalse(result);
	}
	
	@Test
	public void test_all_are_valid_names_07() {
		String[] names = new String[]{ "." };
		boolean result = OscAddress.all_are_valid_names(names);
		assertFalse(result);
	}
	
	@Test
	public void test_all_are_valid_names_08() {
		String[] names = new String[]{ ".." };
		boolean result = OscAddress.all_are_valid_names(names);
		assertFalse(result);
	}
	
	@Test
	public void test_all_are_valid_names_09() {
		String[] names = new String[]{  };
		boolean result = OscAddress.all_are_valid_names(names);
		assertTrue(result);
	}
	
}
