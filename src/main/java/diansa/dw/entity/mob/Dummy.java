package diansa.dw.entity.mob;

import diansa.dw.graphics.AnimatedSprite;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.level.Node;

import java.util.List;

public class Dummy extends Mob {

    private AnimatedSprite stay_side = new AnimatedSprite(SpriteSheet.archer_mob_stay_side, 32, 32, 3);
    // private AnimatedSprite stay_up = new AnimatedSprite(SpriteSheet.archer_mob_stay_up, 32, 32, 3);
    // private AnimatedSprite stay_down = new AnimatedSprite(SpriteSheet.archer_mob_stay_down, 32, 32, 3);
    // private AnimatedSprite stay_upside = new AnimatedSprite(SpriteSheet.archer_mob_stay_upside, 32, 32, 3);

    private AnimatedSprite move_side = new AnimatedSprite(SpriteSheet.archer_mob_move_side, 32, 32, 3);
    private AnimatedSprite move_up = new AnimatedSprite(SpriteSheet.archer_mob_move_up, 32, 32, 3);
    private AnimatedSprite move_down = new AnimatedSprite(SpriteSheet.archer_mob_move_down, 32, 32, 3);
    // private AnimatedSprite move_upside = new AnimatedSprite(SpriteSheet.archer_mob_move_upside, 32, 32, 3);

    private AnimatedSprite animSprite = stay_side;

    private List<Node> path = null;

    private int time = 0;
    int xa = 0;
    int ya = 0;
    private int fireRate = 0;

    public Dummy(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        mobHealth = 100000;
        sprite = animSprite;
    }

    private void move() {

    }

    public void update() {

    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32.0);
    }

}
