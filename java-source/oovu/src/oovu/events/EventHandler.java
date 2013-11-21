package oovu.events;

import oovu.servers.Server;

abstract public class EventHandler {

    public final Server client;
    public final Class<? extends Event> event_class;

    public EventHandler(Server client, Class<? extends Event> event_class) {
        this.client = client;
        this.event_class = event_class;
    }

    abstract public void run(Event event);
}
