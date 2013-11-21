package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;


public class DspReceiveFreedEvent extends Event {

    public DspReceiveFreedEvent(Server publisher) {
        super(publisher);
    }
}
