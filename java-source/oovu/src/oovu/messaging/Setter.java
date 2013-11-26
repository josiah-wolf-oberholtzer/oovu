package oovu.messaging;

import com.cycling74.max.Atom;

public interface Setter {
    public Atom[][] execute(MessageHandler message_handler, Atom[] arguments);
}
