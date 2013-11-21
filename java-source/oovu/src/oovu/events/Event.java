package oovu.events;

import oovu.servers.Server;

public class Event {

    public final Server publisher;

    public Event(Server publisher) {
        this.publisher = publisher;
    }
}
