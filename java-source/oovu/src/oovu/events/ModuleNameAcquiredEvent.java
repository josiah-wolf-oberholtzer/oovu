package oovu.events;

import oovu.servers.Server;


public class ModuleNameAcquiredEvent extends Event {

    public ModuleNameAcquiredEvent(Server publisher) {
        super(publisher);
    }
}
