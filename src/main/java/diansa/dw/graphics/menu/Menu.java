package diansa.dw.graphics.menu;

import diansa.dw.Game;
import diansa.dw.graphics.layers.Layer;
import diansa.dw.input.Mouse;

import java.awt.Graphics;

public abstract class Menu extends Layer {

    protected Game game;
    protected Mouse mouse;

    public void init(Game game, Mouse mouse) {
        this.mouse = mouse;
        this.game = game;
    }

    public abstract void update();

    public abstract void render(Graphics g);
}
