package oovu.messaging;

import oovu.servers.Server;

public abstract class GetterMessageHandler extends MessageHandler {

    public GetterMessageHandler(Server client, String name) {
        super(client, name);
    }

    @Override
    public Integer get_arity() {
        return 0;
    }

    @Override
    public boolean is_binding_relevant() {
        return false;
    }

    @Override
    public boolean is_rampable() {
        return false;
    }
}
