package oovu.tests;

import oovu.Proxy;
import oovu.addresses.Environment;
import oovu.addresses.OscAddress;
import oovu.addresses.OscAddressNode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__Proxy__lifecycle {

    @Before
    public void setUp() throws Exception {
        Environment.reset();
    }

    @After
    public void tearDown() throws Exception {
        Environment.reset();
    }

    @Test
    public void test_01() {
        String osc_address_string = "/foo/bar";
        OscAddress osc_address = OscAddress.from_cache(osc_address_string);
        Atom[] arguments = Atom.newAtom(new String[] {
            osc_address_string
        });
        Proxy proxy = new Proxy(arguments);
        OscAddressNode osc_address_node = Environment.root_osc_address_node
            .search_for_one(osc_address);
        Assert.assertEquals(proxy.get_osc_address_node(), osc_address_node);
        Assert.assertTrue(osc_address_node.get_proxies().contains(proxy));
        Assert.assertArrayEquals(
            Environment.root_osc_address_node.get_summary_pieces(),
            new String[] {
                "/foo", "/foo/bar"
            });
    }
}
