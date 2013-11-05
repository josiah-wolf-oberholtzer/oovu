package oovu.messaging;

public abstract class SetterMessageHandler extends MessageHandler {

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
