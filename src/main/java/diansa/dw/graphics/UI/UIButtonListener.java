package diansa.dw.graphics.UI;

public class UIButtonListener {

    public void entered(UIButton button) {
        button.setColor(0x444444);

    }

    public void exited(UIButton button) {
        button.setColor(0xacacac);

    }

    public void pressed(UIButton button) {
        button.setColor(0xff0000);
    }

    public void released(UIButton button) {
        button.setColor(0xacacac);
    }
}
