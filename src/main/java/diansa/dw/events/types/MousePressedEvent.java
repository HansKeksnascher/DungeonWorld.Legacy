package diansa.dw.events.types;

import diansa.dw.events.Event;

public class MousePressedEvent extends MouseButtonEvent {

    public MousePressedEvent(int button, int x, int y) {
        super(button, x, y, Event.Type.MOUSE_PRESSED);
    }
}
