package oovu.addressing;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import oovu.servers.RootServer;
import oovu.servers.members.AudioServer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Environment {

    public static final ReentrantLock lock = new ReentrantLock();
    private static final Logger logger;
    public static final HashMap<String, AudioServer> pull_addresses = new HashMap<String, AudioServer>();
    public static final HashMap<String, AudioServer> push_addresses = new HashMap<String, AudioServer>();
    public static final RootServer root_server;
    public static final OscAddressNode root_osc_address_node;
    static {
        root_osc_address_node = new OscAddressNode("");
        root_server = new RootServer();
        BasicConfigurator.configure();
        logger = Logger.getLogger("OOVU");
        Environment.logger.setLevel(Level.ALL);
    }

    public static void log(Object message) {
        Environment.logger.log(Environment.logger.getLevel(), message);
    }

    public static void reset() {
        synchronized (Environment.lock) {
            Environment.root_server.clear();
            Environment.root_osc_address_node.clear();
            Environment.pull_addresses.clear();
            Environment.push_addresses.clear();
            Environment.root_server
                .attach_to_osc_address_node(Environment.root_osc_address_node);
        }
    }
}
