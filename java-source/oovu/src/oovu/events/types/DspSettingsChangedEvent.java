package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;

public class DspSettingsChangedEvent extends Event {
    public DspSettingsChangedEvent(Server publisher) {
        super(publisher);
    }

    @Override
    public String toString() {
        return "DspSettingsChangedEvent [publisher=" + this.publisher + "]";
    }
}
