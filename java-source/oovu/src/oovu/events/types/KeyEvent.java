package oovu.events.types;

import oovu.events.Event;

public class KeyEvent extends Event {
    public final int ascii_number;
    public final boolean depressed;

    public KeyEvent(int ascii_number, boolean depressed) {
        this.ascii_number = ascii_number;
        this.depressed = depressed;
    }

    @Override
    public String toString() {
        return "KeyEvent [ascii_number=" + this.ascii_number + ", depressed="
            + this.depressed + "]";
    }
}
