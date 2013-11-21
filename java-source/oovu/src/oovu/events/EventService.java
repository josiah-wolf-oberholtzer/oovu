package oovu.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import oovu.servers.Server;

public class EventService {

    private final Map<Class<? extends Event>, HashSet<Subscription>> subscriptions =
        new HashMap<Class<? extends Event>, HashSet<Subscription>>();

    public void reset() {
        for (Class<? extends Event> event_type : this.subscriptions.keySet()) {
            HashSet<Subscription> subscription_set = this.subscriptions.get(event_type);
            subscription_set.clear();
        }
        this.subscriptions.clear();
    }
    
    public void publish(Event event) {
        for (Class<? extends Event> event_type : this.subscriptions.keySet()) {
            if (!event_type.isInstance(event)) {
                continue;
            }
            HashSet<Subscription> subscription_set =
                this.subscriptions.get(event_type);
            for (Subscription subscription : subscription_set) {
                if (subscription.filter == null) {
                    subscription.subscriber.inform(event);
                } else if (subscription.filter.is_valid_event(event)) {
                    subscription.subscriber.inform(event);
                }
            }
        }
    }

    public void subscribe(
        Server subscriber,
        Class<? extends Event> event_type,
        Filter filter) {
        Subscription subscription =
            new Subscription(subscriber, event_type, filter);
        if (!this.subscriptions.containsKey(event_type)) {
            this.subscriptions.put(event_type, new HashSet<Subscription>());
        }
        HashSet<Subscription> subscription_set =
            this.subscriptions.get(event_type);
        subscription_set.add(subscription);
    }

    public void unsubscribe(Server subscriber) {
        for (Class<? extends Event> event_type : this.subscriptions.keySet()) {
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

    public void unsubscribe(
        Server subscriber,
        Class<? extends Event> event_type,
        Filter filter) {
        Subscription subscription =
            new Subscription(subscriber, event_type, filter);
        if (this.subscriptions.containsKey(event_type)) {
            HashSet<Subscription> subscription_set =
                this.subscriptions.get(event_type);
            if (subscription_set.contains(subscription)) {
                subscription_set.remove(subscription);
            }
            if (0 == subscription_set.size()) {
                this.subscriptions.remove(event_type);
            }
        }
    }
}
