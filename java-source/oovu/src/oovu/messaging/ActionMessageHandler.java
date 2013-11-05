package oovu.messaging;

abstract public class ActionMessageHandler extends MessageHandler {

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
