package oovu.addresses.tests;

import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test__OscAddressNode__get_relative_osc_address {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        OscAddressNode root = new OscAddressNode("");
        OscAddressNode foo =
            root.create_address(OscAddress.from_cache("/foo"), false);
        OscAddressNode bar =
            root.create_address(OscAddress.from_cache("/foo/bar"), false);
        Assert.assertNull(root.get_relative_osc_address_string(root));
        Assert.assertEquals("/foo", foo.get_relative_osc_address_string(root));
        Assert.assertNull(foo.get_relative_osc_address_string(foo));
        Assert.assertEquals("/foo/bar",
            bar.get_relative_osc_address_string(root));
        Assert.assertEquals("/bar", bar.get_relative_osc_address_string(foo));
        Assert.assertNull(bar.get_relative_osc_address_string(bar));
    }
}
