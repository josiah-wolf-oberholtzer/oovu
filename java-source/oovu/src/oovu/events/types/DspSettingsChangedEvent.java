package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;


public class DspSettingsChangedEvent extends Event {

    public DspSettingsChangedEvent(Server publisher) {
        super(publisher);
    }
}
