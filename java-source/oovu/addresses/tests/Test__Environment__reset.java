package oovu.addresses.tests;

import oovu.addresses.Environment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Test__Environment__reset {
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
        // assertEquals(Environment.osc_addresses.size(), 0);
        Assert.assertEquals(Environment.root_server.get_reference_count(), 0);
        Assert.assertEquals(Environment.root_osc_address_node.get_reference_count(), 1);
    }
}
