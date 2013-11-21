package oovu.events;

import oovu.servers.Server;


public class DspSettingsChangedEvent extends Event {

    public DspSettingsChangedEvent(Server publisher) {
        super(publisher);
    }
}
