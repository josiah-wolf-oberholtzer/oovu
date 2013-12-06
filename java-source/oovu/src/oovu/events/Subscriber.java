package oovu.events;

public interface Subscriber {
    public void handle_event(Event event);
}
