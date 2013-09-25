package oovu.tests;

import oovu.Module;
import oovu.environment.Environment;
import oovu.servers.Server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__OOVU__lifecycle {

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
        Atom[] atoms = new Atom[2];
        atoms[0] = Atom.newAtom(1001);
        atoms[1] = Atom.newAtom("foo");
        Module module_client = new Module(atoms);
        Server module_server = module_client.get_server();
        Assert.assertNotNull(module_server);
        Assert.assertNotNull(module_server.get_osc_address_node());
        Assert.assertEquals("foo", module_server.get_name());
        Assert.assertEquals("/foo", module_server.get_osc_address());
        Assert.assertEquals(1, module_server.get_reference_count());
    }
}
