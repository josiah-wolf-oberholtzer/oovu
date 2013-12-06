package oovu.events;

import oovu.events.types.ServerEvent;

public interface Subscriber {
    public void handle_event(ServerEvent event);
}
