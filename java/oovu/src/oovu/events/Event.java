package oovu.events;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.servers.Server;

public class Event {

    protected static Map<EventTypes, Set<Server>> observers = new EnumMap<EventTypes, Set<Server>>(
        EventTypes.class);

    public static void add_observer(EventTypes event_type, Server observer) {
        Set<Server> event_observers = Event.observers.get(event_type);
        if (event_observers == null) {
            event_observers = new HashSet<Server>();
            Event.observers.put(event_type, event_observers);
        }
        event_observers.add(observer);
    }

    public static void clear_observers() {
        Event.observers.clear();
    }

    public static void notify_observers(EventTypes event_type) {
        Set<Server> event_observers = Event.observers.get(event_type);
        if (event_observers != null) {
            for (Server observer : event_observers) {
                observer.handle_event(event_type);
            }
        }
    }

    public static void remove_observer(Server observer) {
        for (Set<Server> event_observers : Event.observers.values()) {
            event_observers.remove(observer);
        }
    }
}
