package oovu.events;

abstract public class Filter {
    abstract public boolean is_valid_event(Event event);
}
