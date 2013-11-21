package oovu.events;

import oovu.servers.Server;


public class DspReceiveFreedEvent extends Event {

    public DspReceiveFreedEvent(Server publisher) {
        super(publisher);
    }
}
