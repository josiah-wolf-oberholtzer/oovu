package oovu.messaging;

public abstract class InfoGetterMessageHandler extends GetterMessageHandler {

    @Override
    public boolean is_meta_relevant() {
        return true;
    }

    @Override
    public boolean is_state_relevant() {
        return false;
    }
}
