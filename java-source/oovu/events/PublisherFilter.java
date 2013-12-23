package oovu.events;

import oovu.servers.Server;

public class PublisherFilter extends Filter {
    public final Server publisher;

    public PublisherFilter(Server publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean is_valid_event(Event event) {
        if (!(event instanceof ServerEvent)) {
            return false;
        }
        ServerEvent server_event = (ServerEvent) event;
        if (server_event.publisher == this.publisher) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PublisherFilter [publisher=" + this.publisher + "]";
    }
}
