package oovu.messaging;

public abstract class InfoGetterMessageHandler extends GetterMessageHandler {

    @Override
    public boolean is_meta_relevant() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean is_state_relevant() {
        // TODO Auto-generated method stub
        return false;
    }
}
