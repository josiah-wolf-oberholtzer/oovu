package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;

public class DspSettingsServerCreatedEvent extends Event {

    public DspSettingsServerCreatedEvent(Server publisher) {
        super(publisher);
    }
}
