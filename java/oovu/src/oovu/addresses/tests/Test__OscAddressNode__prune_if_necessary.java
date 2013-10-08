package oovu.addresses.tests;

import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test__OscAddressNode__prune_if_necessary {

    public static final OscAddressNode root = new OscAddressNode("");

    @Before
    public void setUp() throws Exception {
        Test__OscAddressNode__prune_if_necessary.root.clear();
    }

    @After
    public void tearDown() throws Exception {
        Test__OscAddressNode__prune_if_necessary.root.clear();
    }

    @Test
    public void test_01() {
        OscAddress osc_address = OscAddress.from_cache("/foo/bar/baz/quux");
        OscAddressNode node =
            Test__OscAddressNode__prune_if_necessary.root.create_address(
                osc_address, false);
        Assert.assertArrayEquals(
            Test__OscAddressNode__prune_if_necessary.root.get_summary_pieces(),
            new String[] {
                "/foo", "/foo/bar", "/foo/bar/baz", "/foo/bar/baz/quux"
            });
        node.prune_if_necessary();
        Assert.assertArrayEquals(
            Test__OscAddressNode__prune_if_necessary.root.get_summary_pieces(),
            new String[] {});
    }

    @Test
    public void test_02() {
        OscAddress osc_address = OscAddress.from_cache("/foo/bar/baz/quux");
        OscAddressNode node =
            Test__OscAddressNode__prune_if_necessary.root.create_address(
                osc_address, false);
        Assert.assertArrayEquals(
            Test__OscAddressNode__prune_if_necessary.root.get_summary_pieces(),
            new String[] {
                "/foo", "/foo/bar", "/foo/bar/baz", "/foo/bar/baz/quux"
            });
        node.get_parent().prune_if_necessary();
        Assert.assertArrayEquals(
            Test__OscAddressNode__prune_if_necessary.root.get_summary_pieces(),
            new String[] {
                "/foo", "/foo/bar", "/foo/bar/baz", "/foo/bar/baz/quux"
            });
    }
}
