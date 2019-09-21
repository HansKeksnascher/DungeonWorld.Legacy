package diansa.dw.graphics.UI;

import diansa.dw.util.Vector2i;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UILabel extends UIComponent {

    public String text;
    private Font font;

    public UILabel(Vector2i position, String text) {
        super(position);
        font = new Font("SansSerif", Font.PLAIN, 20);
        this.text = text;
        color = new Color(0xff7768);
    }

    public void setText(String text) {
        this.text = text;
    }

    public UILabel setFont(Font font) {
        this.font = font;
        return this;
    }

    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, position.x + offset.x, position.y + offset.y);
    }

}
