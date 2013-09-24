package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class MessageHandler {

    abstract public String get_name();

    abstract public Atom[][] run(Server context, Atom[] arguments);
    
    public void call_after() { }

    public void call_before() { }
    
}
