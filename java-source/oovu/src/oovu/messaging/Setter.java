package oovu.messaging;

import com.cycling74.max.Atom;

public interface Setter {

    public void execute(BuiltMessageHandler message_handler, Atom[] arguments);
}
