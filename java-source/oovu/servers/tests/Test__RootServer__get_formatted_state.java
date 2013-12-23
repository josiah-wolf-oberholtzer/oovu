package oovu.servers.tests;

import oovu.addresses.Environment;
import oovu.servers.MethodServer;
import oovu.servers.ModuleServer;
import oovu.servers.PropertyServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cycling74.max.Atom;

public class Test__RootServer__get_formatted_state {
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
        ModuleServer filter_module = ModuleServer.allocate(1001);
        filter_module.acquire_name("filter~");
        @SuppressWarnings("unused")
        PropertyServer filter_type =
            PropertyServer
                .allocate(1001, "type", Atom
                    .parse(":datatype option :options hp lp bp br :default hp"));
        @SuppressWarnings("unused")
        PropertyServer filter_frequency =
            PropertyServer
                .allocate(1001, "frequency", Atom
                    .parse(":datatype decimal :range 20. 22050. :default 1000"));
        @SuppressWarnings("unused")
        PropertyServer filter_amplitude =
            PropertyServer.allocate(1001, "amplitude",
                Atom.parse(":datatype decimal :range 0. 1. :default 1."));
        @SuppressWarnings("unused")
        MethodServer filter_clear =
            MethodServer.allocate(1001, "clear", Atom.parse(""));
        ModuleServer delay_module = ModuleServer.allocate(2002);
        delay_module.acquire_name("delay~");
        @SuppressWarnings("unused")
        PropertyServer delay_length =
            PropertyServer.allocate(2002, "length",
                Atom.parse(":datatype decimal :range 0. 1000. :default 100"));
        @SuppressWarnings("unused")
        PropertyServer delay_feedback =
            PropertyServer.allocate(2002, "feedback",
                Atom.parse(":datatype decimal :range 0. 0.999 :default 0.001"));
        Atom[][] actuals = Environment.root_server.get_formatted_state();
        Atom[][] expecteds =
            new Atom[][] {
                Atom.parse("wclose"),
                Atom.parse("clear"),
                Atom.parse("CUE NewCue"),
                Atom.parse("cr"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("### /delay~ ###"),
                Atom.parse("cr"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/feedback/:pattern"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/feedback/:maximum 0.999"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/feedback/:minimum 0.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/feedback 0.0010"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/length/:pattern"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/length/:maximum 1000.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/length/:minimum 0.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/delay~/length 100.0"),
                Atom.parse("cr"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("### /filter~ ###"),
                Atom.parse("cr"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/amplitude/:pattern"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/amplitude/:maximum 1.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/amplitude/:minimum 0.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/amplitude 1.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/frequency/:pattern"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/frequency/:maximum 22050.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/frequency/:minimum 20.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/frequency 1000.0"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/type/:pattern"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/type/:options hp lp bp br"),
                Atom.parse("cr"),
                Atom.parse("tab"),
                Atom.parse("/filter~/type hp"),
                Atom.parse("cr"),
                Atom.parse("open")
            };
        Assert.assertArrayEquals(expecteds, actuals);
    }
}
