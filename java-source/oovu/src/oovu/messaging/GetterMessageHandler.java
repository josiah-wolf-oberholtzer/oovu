package oovu.messaging;

public abstract class GetterMessageHandler extends MessageHandler {

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
