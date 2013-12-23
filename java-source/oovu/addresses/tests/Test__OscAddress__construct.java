package oovu.addresses.tests;

import oovu.addresses.Environment;
import oovu.addresses.OscAddress;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test__OscAddress__construct {
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
        this.thrown.expect(RuntimeException.class);
        OscAddress osc_address = OscAddress.from_cache(address_string);
        Assert.assertNotNull(osc_address);
    }

    @Test
    public void test_construct_02() {
        OscAddress osc_address = OscAddress.from_cache("");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_03() {
        OscAddress osc_address = OscAddress.from_cache(".");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_04() {
        OscAddress osc_address = OscAddress.from_cache("..");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            ".."
        });
    }

    @Test
    public void test_construct_06() {
        OscAddress osc_address = OscAddress.from_cache("/");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_07() {
        OscAddress osc_address = OscAddress.from_cache("./");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_08() {
        OscAddress osc_address = OscAddress.from_cache(".//./././//./");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_09() {
        OscAddress osc_address = OscAddress.from_cache(":attribute");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, "attribute");
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_10() {
        OscAddress osc_address = OscAddress.from_cache("./:attribute");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, "attribute");
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_11() {
        OscAddress osc_address =
            OscAddress.from_cache("/:attribute1/attribute2");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name,
            "attribute1/attribute2");
        Assert.assertArrayEquals(osc_address.address_items, new String[0]);
    }

    @Test
    public void test_construct_12() {
        OscAddress osc_address = OscAddress.from_cache("foo");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo"
        });
    }

    @Test
    public void test_construct_13() {
        OscAddress osc_address = OscAddress.from_cache("./foo");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo"
        });
    }

    @Test
    public void test_construct_14() {
        OscAddress osc_address = OscAddress.from_cache("../foo");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "..", "foo"
        });
    }

    @Test
    public void test_construct_15() {
        OscAddress osc_address = OscAddress.from_cache("foo/bar/baz");
        Assert.assertEquals(osc_address.is_relative, true);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo", "bar", "baz"
        });
    }

    @Test
    public void test_construct_16() {
        OscAddress osc_address = OscAddress.from_cache("/foo/bar/baz");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo", "bar", "baz"
        });
    }

    @Test
    public void test_construct_17() {
        OscAddress osc_address = OscAddress.from_cache("/foo/../baz");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, null);
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo", "..", "baz"
        });
    }

    @Test
    public void test_construct_18() {
        OscAddress osc_address = OscAddress.from_cache("/foo/../baz/:quux");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, "quux");
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo", "..", "baz"
        });
    }

    @Test
    public void test_construct_19() {
        OscAddress osc_address =
            OscAddress.from_cache("/foo.x/../baz/:quux.1/wux");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, "quux.1/wux");
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo.x", "..", "baz"
        });
    }

    @Test
    public void test_construct_20() {
        OscAddress osc_address = OscAddress.from_cache("/foo.x/../:quux.1/wux");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, "quux.1/wux");
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo.x", ".."
        });
    }

    @Test
    public void test_construct_21() {
        OscAddress osc_address =
            OscAddress.from_cache("/foo.x/./..//:quux.1/wux");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, "quux.1/wux");
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo.x", ".."
        });
    }

    @Test
    public void test_construct_22() {
        OscAddress osc_address =
            OscAddress.from_cache("/foo.*/../*.bar/baz/*/:quux.1/wux");
        Assert.assertEquals(osc_address.is_relative, false);
        Assert.assertEquals(osc_address.message_handler_name, "quux.1/wux");
        Assert.assertArrayEquals(osc_address.address_items, new String[] {
            "foo.*", "..", "*.bar", "baz", "*"
        });
    }
}
