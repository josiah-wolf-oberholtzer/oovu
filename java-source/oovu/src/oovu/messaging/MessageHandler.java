package oovu.messaging;

import oovu.servers.Server;

import com.cycling74.max.Atom;

abstract public class MessageHandler {
    protected final Server client;
    protected final String name;

    public MessageHandler(Server client, String name) {
        this.client = client;
        this.name = name;
    }

    public void call_after() {
    }

    abstract public Integer get_arity();

    public String get_name() {
        return this.name;
    }

    abstract public boolean is_binding_relevant();

    abstract public boolean is_meta_relevant();

    abstract public boolean is_rampable();

    abstract public boolean is_state_relevant();

    abstract public Atom[][] run(Atom[] arguments);
}
