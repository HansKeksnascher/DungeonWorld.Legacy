package diansa.dw.events.types;

import diansa.dw.events.Event;

public class MouseReleasedEvent extends MouseButtonEvent {


    public MouseReleasedEvent(int button, int x, int y) {
        super(button, x, y, Event.Type.MOUSE_RELEASED);
    }
}
