package oovu.messaging;


public abstract class GetterMessageHandler extends MessageHandler {

    @Override
    public int get_arity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean is_binding_relevant() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean is_rampable() {
        // TODO Auto-generated method stub
        return false;
    }

}
