package oovu.addresses;

import java.util.concurrent.locks.ReentrantLock;

import oovu.events.ClockEventService;
import oovu.events.EventService;
import oovu.events.MidiEventService;
import oovu.servers.DspReceiveServer;
import oovu.servers.RootServer;

// import org.apache.log4j.BasicConfigurator;
// import org.apache.log4j.Level;
// import org.apache.log4j.Logger;
import com.cycling74.max.Atom;
import com.cycling74.max.Executable;
import com.cycling74.max.MaxObject;
import com.cycling74.max.MaxSystem;

public class Environment {
    public static final ReentrantLock lock = new ReentrantLock();
    // private static final Logger logger;
    public static final RootServer root_server;
    public static final OscAddressNode root_osc_address_node;
    public static final EventService event_service;
    public static final MidiEventService midi_event_service;
    public static final ClockEventService clock_event_service;
    static {
        root_osc_address_node = new OscAddressNode("");
        root_server = new RootServer();
        event_service = new EventService();
        clock_event_service = new ClockEventService(Environment.event_service);
        midi_event_service = new MidiEventService(Environment.event_service);
        // BasicConfigurator.configure();
        // logger = Logger.getLogger("OOVU");
        // Environment.logger.setLevel(Level.ALL);
    }

    public static void defer_low(Executable executable) {
        try {
            MaxSystem.deferLow(executable);
        } catch (UnsatisfiedLinkError e) {
            Environment.log(e);
        }
    }

    public static void log(Object message) {
        try {
            MaxObject.post(message.toString());
        } catch (UnsatisfiedLinkError e) {
            // Environment.logger.log(Environment.logger.getLevel(), message);
        }
    }

    public static void outlet(MaxObject max_object, int outlet_index, Atom[] payload) {
        try {
            max_object.outlet(outlet_index, payload);
        } catch (UnsatisfiedLinkError e) {
            Environment.log(e);
        }
    }

    public static void report() {
        for (String piece : Environment.root_osc_address_node.get_debug_pieces()) {
            Environment.log(piece);
        }
    }

    public static void reset() {
        synchronized (Environment.lock) {
            Environment.root_server.clear();
            Environment.root_osc_address_node.clear();
            Environment.root_server
                .attach_to_osc_address_node(Environment.root_osc_address_node);
            Environment.event_service.reset();
            DspReceiveServer.dsp_receive_servers.clear();
        }
    }
}
