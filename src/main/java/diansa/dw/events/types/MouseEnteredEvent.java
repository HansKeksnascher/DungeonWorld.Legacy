package diansa.dw.events.types;

import diansa.dw.events.Event;

public class MouseEnteredEvent extends Event {
    private int x, y;
    private boolean entered;

    public MouseEnteredEvent(int x, int y, boolean entered) {
        super(Event.Type.MOUSE_ENTERED);
        this.x = x;
        this.y = y;
        this.entered = entered;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getEntered() {
        return entered;
    }

}
