package oovu.messaging;

import oovu.servers.Server;

public abstract class InfoGetterMessageHandler extends GetterMessageHandler {

    public InfoGetterMessageHandler(Server client, String name) {
        super(client, name);
    }

    @Override
    public boolean is_meta_relevant() {
        return true;
    }

    @Override
    public boolean is_state_relevant() {
        return false;
    }
}
