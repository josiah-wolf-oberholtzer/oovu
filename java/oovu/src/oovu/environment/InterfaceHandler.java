package oovu.environment;

import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class InterfaceHandler {

    abstract public String get_name();

    abstract public Atom[][] run(Server context, Atom[] arguments);

}
