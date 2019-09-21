package diansa.dw.graphics;

import diansa.dw.graphics.UI.UIPanel;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class UIManager {

    private List<UIPanel> panels = new ArrayList<UIPanel>();

    public UIManager() {

    }

    public void addPanel(UIPanel panel) {
        panels.add(panel);
    }

    public void removePanel(UIPanel panel) {
        panels.remove(panel);
    }

    public void update() {
        for (int i = 0; i < panels.size(); i++) {
            panels.get(i).update();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < panels.size(); i++) {
            panels.get(i).render(g);
        }
    }
}
