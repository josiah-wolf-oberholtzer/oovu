package oovu.addresses.tests;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test__OscAddress__all_are_node_name_tokens {
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
    public void test_all_are_node_name_tokens_01() {
        String[] names = new String[] {
            "aaa", "bbb", "ccc"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertTrue(result);
    }

    @Test
    public void test_all_are_node_name_tokens_02() {
        String[] names = new String[] {
            "one1one", "two2two", "three3three"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertTrue(result);
    }

    @Test
    public void test_all_are_node_name_tokens_03() {
        String[] names = new String[] {
            "axis.x", "axis.y"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertTrue(result);
    }

    @Test
    public void test_all_are_node_name_tokens_04() {
        String[] names = new String[] {
            "send.1", "send.2"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertTrue(result);
    }

    @Test
    public void test_all_are_node_name_tokens_05() {
        String[] names = new String[] {
            "1", "2", "3"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertFalse(result);
    }

    @Test
    public void test_all_are_node_name_tokens_06() {
        String[] names = new String[] {
            "foo/bar"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertFalse(result);
    }

    @Test
    public void test_all_are_node_name_tokens_07() {
        String[] names = new String[] {
            "."
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertFalse(result);
    }

    @Test
    public void test_all_are_node_name_tokens_08() {
        String[] names = new String[] {
            ".."
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertFalse(result);
    }

    @Test
    public void test_all_are_node_name_tokens_09() {
        String[] names = new String[] {
            ""
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertFalse(result);
    }

    @Test
    public void test_all_are_node_name_tokens_10() {
        String[] names = new String[] {};
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertTrue(result);
    }

    @Test
    public void test_all_are_node_name_tokens_11() {
        String[] names = new String[] {
            "*", "foo.*", "*.foo", "*.*"
        };
        boolean result = OscAddress.all_are_node_name_tokens(names);
        Assert.assertTrue(result);
    }
}
