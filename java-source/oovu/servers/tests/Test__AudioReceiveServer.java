package oovu.servers.tests;

import oovu.addresses.Environment;
import oovu.servers.DspReceiveServer;
import oovu.servers.ModuleServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__AudioReceiveServer {
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
        DspReceiveServer audio_receive_server_one =
            DspReceiveServer.allocate(1001, "receive~.1", new Atom[0]);
        Assert.assertEquals(0, DspReceiveServer.dsp_receive_servers.size());
        ModuleServer module_server = ModuleServer.allocate(1001);
        Assert.assertEquals(0, DspReceiveServer.dsp_receive_servers.size());
        module_server.acquire_name("foo");
        Assert.assertEquals(1, DspReceiveServer.dsp_receive_servers.size());
        Assert.assertTrue(DspReceiveServer.dsp_receive_servers
            .containsKey(audio_receive_server_one.get_osc_address()));
        Assert.assertTrue(DspReceiveServer.dsp_receive_servers
            .containsValue(audio_receive_server_one));
        DspReceiveServer audio_receive_server_two =
            DspReceiveServer.allocate(1001, "receive~.2", new Atom[0]);
        Assert.assertEquals(2, DspReceiveServer.dsp_receive_servers.size());
        Assert.assertTrue(DspReceiveServer.dsp_receive_servers
            .containsKey(audio_receive_server_two.get_osc_address()));
        Assert.assertTrue(DspReceiveServer.dsp_receive_servers
            .containsValue(audio_receive_server_two));
        audio_receive_server_one.deallocate_if_necessary();
        Assert.assertEquals(1, DspReceiveServer.dsp_receive_servers.size());
        Assert.assertTrue(DspReceiveServer.dsp_receive_servers
            .containsKey(audio_receive_server_two.get_osc_address()));
        Assert.assertTrue(DspReceiveServer.dsp_receive_servers
            .containsValue(audio_receive_server_two));
        audio_receive_server_two.deallocate_if_necessary();
        Assert.assertEquals(0, DspReceiveServer.dsp_receive_servers.size());
    }
}
