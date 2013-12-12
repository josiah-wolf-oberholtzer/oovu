package oovu.events;

import oovu.servers.Server;

public class DspReceiversChangedEvent extends ServerEvent {
    public DspReceiversChangedEvent(Server publisher) {
        super(publisher);
    }

    @Override
    public String toString() {
        return "DspReceiversChangedEvent [publisher=" + this.publisher + "]";
    }
}
