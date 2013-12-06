package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;

public class ServerEvent extends Event {
    public final Server publisher;

    public ServerEvent(Server publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Event [publisher=" + this.publisher + "]";
    }
}
