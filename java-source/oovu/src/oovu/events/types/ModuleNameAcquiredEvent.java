package oovu.events.types;

import oovu.events.Event;
import oovu.servers.Server;

public class ModuleNameAcquiredEvent extends Event {
    public ModuleNameAcquiredEvent(Server publisher) {
        super(publisher);
    }

    @Override
    public String toString() {
        return "ModuleNameAcquiredEvent [publisher=" + this.publisher + "]";
    }
}
