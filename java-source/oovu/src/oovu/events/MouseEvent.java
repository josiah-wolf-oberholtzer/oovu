package oovu.events;

public class MouseEvent extends Event {
    public final boolean clicked;
    public final int delta_clicked;
    public final int x;
    public final int y;
    public final int delta_x;
    public final int delta_y;

    public MouseEvent(
        boolean clicked,
        int delta_clicked,
        int x,
        int y,
        int delta_x,
        int delta_y) {
        this.clicked = clicked;
        this.delta_clicked = delta_clicked;
        this.x = x;
        this.y = y;
        this.delta_x = delta_x;
        this.delta_y = delta_y;
    }

    @Override
    public String toString() {
        return "MouseEvent [clicked=" + this.clicked + ", delta_clicked="
            + this.delta_clicked + ", x=" + this.x + ", y=" + this.y
            + ", delta_x=" + this.delta_x + ", delta_y=" + this.delta_y + "]";
    }
}
