package diansa.dw.graphics.UI;

import diansa.dw.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UIPanel extends UIComponent {

    private List<UIComponent> components = new ArrayList<UIComponent>();
    private Vector2i size;
    private BufferedImage img;
    private boolean showImage = false;

    public UIPanel(Vector2i position, Vector2i size) {
        super(position);
        this.position = position;
        this.size = size;
        color = new Color(0xff6B728E);
    }

    public UIPanel(Vector2i position, Vector2i size, boolean showImg, String path) {
        super(position);
        this.position = position;
        this.size = size;
        this.showImage = showImg;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addComponent(UIComponent component) {
        component.init(this);
        components.add(component);
    }

    public void update() {
        for (UIComponent component : components) {
            component.setOffset(position);
            component.update();
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(position.x, position.y, size.x, size.y);
        if (showImage == true) g.drawImage(img, position.x, position.y, null);
        for (UIComponent component : components) {
            component.render(g);
        }

    }
}
