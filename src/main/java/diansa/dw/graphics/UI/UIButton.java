package diansa.dw.graphics.UI;

import diansa.dw.input.Mouse;
import diansa.dw.util.Vector2i;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton extends UIComponent {

    private UIButtonListener buttonListener;
    private UIActionListener actionListener;
    public UILabel label;

    private Image image;

    private boolean inside = false;
    private boolean pressed = false;

    public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
        super(position, size);
        this.actionListener = actionListener;
        Vector2i lp = new Vector2i(position);
        lp.x += 4;
        lp.y += size.y - 10;
        label = new UILabel(lp, "");
        label.setColor(0x000000);
        label.active = false;
        init();

    }

    public UIButton(Vector2i position, BufferedImage image, UIActionListener actionListener) {
        super(position, new Vector2i(image.getWidth(), image.getHeight()));
        this.actionListener = actionListener;
        setImage(image);
        init();
    }

    private void init() {
        setColor(0xcacaca);

        buttonListener = new UIButtonListener();
    }

    void init(UIPanel panel) {
        super.init(panel);
        if (label != null)
            panel.addComponent(label);
    }

    public void setButtonListener(UIButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setText(String text) {
        if (text == "") {
            label.active = false;
        } else {
            label.text = text;
        }
    }

    public void update() {
        Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
        if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
            if (!inside) {
                buttonListener.entered(this);

            }
            inside = true;

            if (!pressed && Mouse.getButton() == MouseEvent.BUTTON1) {
                buttonListener.pressed(this);
                pressed = true;
            } else if (pressed && Mouse.getButton() == MouseEvent.NOBUTTON) {
                buttonListener.released(this);
                actionListener.perform();
                pressed = false;
            }
        } else {
            if (inside) {
                buttonListener.exited(this);
                pressed = false;
            }
            inside = false;
        }
    }

    public void render(Graphics g) {
        int x = position.x + offset.x;
        int y = position.y + offset.y;
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.setColor(color);
            g.fillRect(x, y, size.x, size.y);

            if (label != null)
                label.render(g);
        }
    }
}
