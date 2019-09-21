package diansa.dw.level.tile;

import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class SolidTile extends Tile {

    public SolidTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

    public boolean solid() {
        return true;
    }
}
