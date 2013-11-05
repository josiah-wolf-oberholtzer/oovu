package oovu.messaging;


abstract public class ActionMessageHandler extends MessageHandler {

    @Override
    public boolean is_meta_relevant() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean is_binding_relevant() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean is_state_relevant() {
        // TODO Auto-generated method stub
        return false;
    }

}
