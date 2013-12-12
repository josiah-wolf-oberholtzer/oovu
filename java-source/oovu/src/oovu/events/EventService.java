package oovu.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oovu.addresses.Environment;
import oovu.servers.Server;

public class EventService {
    private final Map<Class<? extends Event>, HashSet<Subscription>> subscriptions =
        new HashMap<Class<? extends Event>, HashSet<Subscription>>();

    public void publish(Event event) {
        if (!(event instanceof ClockEvent)) {
            Environment.log("Published: " + event.toString());
        }
        Set<Class<? extends Event>> keys =
            new HashSet<Class<? extends Event>>(this.subscriptions.keySet());
        for (Class<? extends Event> event_type : keys) {
            if (!event_type.isInstance(event)) {
                continue;
            }
            HashSet<Subscription> subscription_set =
                new HashSet<Subscription>(this.subscriptions.get(event_type));
            for (Subscription subscription : subscription_set) {
                if (subscription.filter == null) {
                    subscription.handle_event(event);
                } else if (subscription.filter.is_valid_event(event)) {
                    subscription.handle_event(event);
                }
            }
        }
    }

    public void reset() {
        for (Class<? extends Event> event_type : this.subscriptions.keySet()) {
            HashSet<Subscription> subscription_set =
                this.subscriptions.get(event_type);
            subscription_set.clear();
        }
        this.subscriptions.clear();
    }

    public void subscribe(Subscription subscription) {
        if (!this.subscriptions.containsKey(subscription.event_class)) {
            this.subscriptions.put(subscription.event_class,
                new HashSet<Subscription>());
        }
        HashSet<Subscription> subscription_set =
            this.subscriptions.get(subscription.event_class);
        subscription_set.add(subscription);
    }

    public void unsubscribe(Server subscriber) {
        Set<Class<? extends Event>> keys =
            new HashSet<Class<? extends Event>>(this.subscriptions.keySet());
        for (Class<? extends Event> event_type : keys) {
            HashSet<Subscription> old_subscription_set =
                this.subscriptions.get(event_type);
            HashSet<Subscription> new_subscription_set =
                new HashSet<Subscription>(old_subscription_set);
            for (Subscription subscription : new_subscription_set) {
                if (subscription.subscriber == subscriber) {
                    old_subscription_set.remove(subscription);
                }
            }
            if (0 == old_subscription_set.size()) {
                this.subscriptions.remove(event_type);
            }
        }
    }

    public void unsubscribe(Subscription subscription) {
        if (this.subscriptions.containsKey(subscription.event_class)) {
            HashSet<Subscription> subscription_set =
                this.subscriptions.get(subscription.event_class);
            if (subscription_set.contains(subscription)) {
                subscription_set.remove(subscription);
            }
            if (0 == subscription_set.size()) {
                this.subscriptions.remove(subscription.event_class);
            }
        }
    }
}
