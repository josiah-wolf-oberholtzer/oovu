package oovu.events;

import oovu.addresses.Environment;
import oovu.events.types.ServerEvent;
import oovu.servers.Server;

abstract public class Subscription {
    public final Server subscriber;
    public final Class<? extends ServerEvent> event_class;
    public final Filter filter;

    public Subscription(
        Server subscriber,
        Class<? extends ServerEvent> event_class,
        Filter filter) {
        this.subscriber = subscriber;
        this.event_class = event_class;
        this.filter = filter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Subscription other = (Subscription) obj;
        if (this.event_class == null) {
            if (other.event_class != null) {
                return false;
            }
        } else if (!this.event_class.equals(other.event_class)) {
            return false;
        }
        if (this.filter == null) {
            if (other.filter != null) {
                return false;
            }
        } else if (!this.filter.equals(other.filter)) {
            return false;
        }
        if (this.subscriber == null) {
            if (other.subscriber != null) {
                return false;
            }
        } else if (!this.subscriber.equals(other.subscriber)) {
            return false;
        }
        return true;
    }

    abstract public void handle_event(ServerEvent event);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            (prime * result)
                + ((this.event_class == null) ? 0 : this.event_class.hashCode());
        result =
            (prime * result)
                + ((this.filter == null) ? 0 : this.filter.hashCode());
        result =
            (prime * result)
                + ((this.subscriber == null) ? 0 : this.subscriber.hashCode());
        return result;
    }

    public Subscription subscribe() {
        // Environment.log("SUBSCRIBED: " + this.toString());
        Environment.event_service.subscribe(this);
        this.subscriber.add_subscription(this);
        return this;
    }

    @Override
    public String toString() {
        return "Subscription [subscriber=" + this.subscriber + ", event_class="
            + this.event_class.getSimpleName() + ", filter=" + this.filter
            + "]";
    }

    public Subscription unsubscribe() {
        // Environment.log("UNSUBSCRIBED: " + this.toString());
        Environment.event_service.unsubscribe(this);
        this.subscriber.remove_subscription(this);
        return this;
    }
}
