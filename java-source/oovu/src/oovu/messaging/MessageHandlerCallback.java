package oovu.messaging;

import com.cycling74.max.Atom;

public interface MessageHandlerCallback {
    public Atom[][] execute(MessageHandler message_handler, Atom[] arguments);
}
