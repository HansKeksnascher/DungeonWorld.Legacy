package diansa.dw.input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import diansa.dw.events.EventListener;
import diansa.dw.events.types.MouseEnteredEvent;
import diansa.dw.events.types.MouseMovedEvent;
import diansa.dw.events.types.MousePressedEvent;
import diansa.dw.events.types.MouseReleasedEvent;

public class Mouse implements MouseListener {

    private static int mouseX = -1;
    private static int mouseY = -1;
    private static int mouseB = -1;

    private EventListener eventListener;

    public Mouse(EventListener listener) {
        eventListener = listener;
    }

    public static int getX() {
        return mouseX;
    }

    public static int getY() {
        return mouseY;
    }

    public static int getButton() {
        return mouseB;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), true);
        eventListener.onEvent(event);
    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), false);
        eventListener.onEvent(event);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
        MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
        eventListener.onEvent(event);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
        MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
        eventListener.onEvent(event);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        MouseEnteredEvent event = new MouseEnteredEvent(e.getX(), e.getY(), true);
        eventListener.onEvent(event);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        MouseEnteredEvent event = new MouseEnteredEvent(e.getX(), e.getY(), false);
        eventListener.onEvent(event);
    }
}
