package diansa.dw.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Keyboard implements KeyListener {

    protected static boolean[] keys = new boolean[120];
    public boolean up, down, left, right, action, character, inventory;

    public void update() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        action = keys[KeyEvent.VK_E];
        character = keys[KeyEvent.VK_C];
        inventory = keys[KeyEvent.VK_B];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.isAutoRepeat()) {
            return;
        }
        keys[e.getKeyCode()] = false;
    }
}
