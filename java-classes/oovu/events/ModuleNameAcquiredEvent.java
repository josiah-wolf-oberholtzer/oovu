package oovu.events;

import oovu.servers.Server;

public class ModuleNameAcquiredEvent extends ServerEvent {
    public ModuleNameAcquiredEvent(Server publisher) {
        super(publisher);
    }

    @Override
    public String toString() {
        return "ModuleNameAcquiredEvent [publisher=" + this.publisher + "]";
    }
}
