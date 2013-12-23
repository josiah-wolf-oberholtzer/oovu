package oovu.events;

public class KeyFilter extends Filter {
    public final int ascii_number;

    public KeyFilter(int ascii_number) {
        this.ascii_number = ascii_number;
    }

    @Override
    public boolean is_valid_event(Event event) {
        KeyEvent key_event = (KeyEvent) event;
        if (key_event.ascii_number == this.ascii_number) {
            return true;
        }
        return false;
    }
}
