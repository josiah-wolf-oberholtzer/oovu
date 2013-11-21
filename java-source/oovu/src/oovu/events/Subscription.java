package oovu.events;

public class Subscription {

    public final Subscriber subscriber;
    public final Class<? extends Event> event_type;
    public final Filter filter;

    public Subscription(Subscriber subscriber,
        Class<? extends Event> event_type, Filter filter) {
        this.subscriber = subscriber;
        this.event_type = event_type;
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
        if (this.event_type == null) {
            if (other.event_type != null) {
                return false;
            }
        } else if (!this.event_type.equals(other.event_type)) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
            (prime * result)
                + ((this.event_type == null) ? 0 : this.event_type.hashCode());
        result =
            (prime * result)
                + ((this.filter == null) ? 0 : this.filter.hashCode());
        result =
            (prime * result)
                + ((this.subscriber == null) ? 0 : this.subscriber.hashCode());
        return result;
    }
}
