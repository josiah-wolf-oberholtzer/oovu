package oovu.events;

import oovu.servers.Server;

public class DspSettingsChangedEvent extends ServerEvent {
    public DspSettingsChangedEvent(Server publisher) {
        super(publisher);
    }

    @Override
    public String toString() {
        return "DspSettingsChangedEvent [publisher=" + this.publisher + "]";
    }
}
