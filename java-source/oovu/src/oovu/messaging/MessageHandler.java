package oovu.messaging;

import com.cycling74.max.Atom;

abstract public class MessageHandler {

    public void call_after() {
    }

    abstract public Integer get_arity();

    abstract public String get_name();

    abstract public boolean is_binding_relevant();

    abstract public boolean is_meta_relevant();

    abstract public boolean is_rampable();

    abstract public boolean is_state_relevant();

    abstract public Atom[][] run(Atom[] arguments);
}
