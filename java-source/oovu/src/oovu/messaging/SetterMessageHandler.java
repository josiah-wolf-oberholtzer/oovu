package oovu.messaging;

import oovu.servers.Server;

public abstract class SetterMessageHandler extends MessageHandler {
    public SetterMessageHandler(Server client, String name) {
        super(client, name);
    }

    @Override
    public boolean is_binding_relevant() {
        return false;
    }

    @Override
    public boolean is_meta_relevant() {
        return false;
    }

    @Override
    public boolean is_rampable() {
        return false;
    }

    @Override
    public boolean is_state_relevant() {
        return false;
    }
}
