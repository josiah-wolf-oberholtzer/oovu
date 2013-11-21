package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;

public class DspReceiversChangedEvent extends Event {

    public DspReceiversChangedEvent(Server publisher) {
        super(publisher);
    }
}
