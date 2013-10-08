package oovu.events;

abstract public class EventHandler {

    abstract public EventTypes get_event();

    abstract public void run();
}
