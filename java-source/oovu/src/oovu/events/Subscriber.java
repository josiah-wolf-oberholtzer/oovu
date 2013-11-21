package oovu.events;

abstract public interface Subscriber {

    abstract public void inform(Event event);
}
