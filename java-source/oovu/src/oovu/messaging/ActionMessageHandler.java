package oovu.messaging;

import oovu.servers.Server;

abstract public class ActionMessageHandler extends MessageHandler {

    public ActionMessageHandler(Server client, String name) {
        super(client, name);
    }

    @Override
    public boolean is_binding_relevant() {
        return true;
    }

    @Override
    public boolean is_meta_relevant() {
        return false;
    }

    @Override
    public boolean is_state_relevant() {
        return false;
    }
}
