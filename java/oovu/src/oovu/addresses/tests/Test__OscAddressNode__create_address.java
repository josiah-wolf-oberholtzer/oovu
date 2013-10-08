package oovu.addresses.tests;

import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test__OscAddressNode__create_address {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    public static final OscAddressNode root = new OscAddressNode("");

    @Before
    public void setUp() throws Exception {
        Test__OscAddressNode__create_address.root.clear();
    }

    @After
    public void tearDown() throws Exception {
        Test__OscAddressNode__create_address.root.clear();
    }

    @Test
    public void test_01() {
        OscAddress osc_address = OscAddress.from_cache("/foo");
        OscAddressNode foo =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertEquals("foo", foo.get_name());
        Assert.assertEquals(foo,
            Test__OscAddressNode__create_address.root.get_named_child("foo"));
        Assert.assertArrayEquals(new String[] {
            "/foo"
        }, Test__OscAddressNode__create_address.root.get_summary_pieces());
    }

    @Test
    public void test_02() {
        OscAddress osc_address = OscAddress.from_cache("/foo");
        OscAddressNode original = new OscAddressNode("foo");
        OscAddressNode created = null;
        Test__OscAddressNode__create_address.root.add_child(original);
        created =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, false);
        Assert.assertEquals(original, created);
        created =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertNotEquals(created, original);
        Assert.assertEquals(created.get_name(), "foo.1");
    }

    @Test
    public void test_03() {
        OscAddress osc_address = OscAddress.from_cache("/foo/bar");
        OscAddressNode node_1 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, false);
        Assert.assertEquals(node_1.get_osc_address_string(), "/foo/bar");
        OscAddressNode node_2 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, false);
        Assert.assertEquals(node_1, node_2);
        OscAddressNode node_3 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertNotEquals(node_3, node_1);
        Assert.assertEquals(node_3.get_osc_address_string(), "/foo/bar.1");
    }

    @Test
    public void test_04() {
        OscAddress osc_address = OscAddress.from_cache("/foo/bar/baz");
        OscAddressNode node_1 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertEquals(node_1.get_osc_address_string(), "/foo/bar/baz");
        OscAddressNode node_2 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertEquals(node_2.get_osc_address_string(), "/foo/bar/baz.1");
        OscAddressNode node_3 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertEquals(node_3.get_osc_address_string(), "/foo/bar/baz.2");
        OscAddressNode node_4 =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertEquals(node_4.get_osc_address_string(), "/foo/bar/baz.3");
        Assert.assertArrayEquals(
            Test__OscAddressNode__create_address.root.get_summary_pieces(),
            new String[] {
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
        OscAddress osc_address_one = OscAddress.from_cache("/foo/bar");
        OscAddress osc_address_two = OscAddress.from_cache("baz/quux");
        OscAddressNode foo_bar =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address_one, false);
        Assert.assertEquals(foo_bar.get_osc_address_string(), "/foo/bar");
        OscAddressNode foo_bar_baz_quux =
            foo_bar.create_address(osc_address_two, false);
        Assert.assertEquals(foo_bar_baz_quux.get_osc_address_string(),
            "/foo/bar/baz/quux");
        OscAddressNode baz_quux =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address_two, false);
        Assert.assertEquals(baz_quux.get_osc_address_string(), "/baz/quux");
    }

    @Test
    public void test_06() {
        OscAddress osc_address = OscAddress.from_cache("/foo/*/baz");
        this.thrown.expect(RuntimeException.class);
        OscAddressNode created =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertNull(created);
    }

    @Test
    public void test_07() {
        OscAddress osc_address = OscAddress.from_cache("/foo/../baz");
        this.thrown.expect(RuntimeException.class);
        OscAddressNode created =
            Test__OscAddressNode__create_address.root.create_address(
                osc_address, true);
        Assert.assertNull(created);
    }
}
