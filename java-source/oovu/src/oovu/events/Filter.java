package oovu.events;

import oovu.events.types.ServerEvent;

abstract public class Filter {
    abstract public boolean is_valid_event(ServerEvent event);
}
