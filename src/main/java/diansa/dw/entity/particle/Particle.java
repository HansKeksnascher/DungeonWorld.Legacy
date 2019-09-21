package diansa.dw.entity.particle;

import diansa.dw.entity.Entity;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class Particle extends Entity {

    private Sprite sprite;

    private int duration;
    private int time = 0;

    protected double xx, yy, zz, xa, ya, za;

    public Particle(int x, int y, int duration, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.duration = duration;
        this.sprite = sprite;

        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();
        this.zz = 1.0;
    }

    public void update() {
        time++;
        if (time >= 8000) time = 0;
        if (time > duration) remove();
        za -= 0.1;
        if (zz < 0) {
            zz = 0;
            za *= -0.8;
            xa *= 0.2;
            ya *= 0.2;
        }

        move(xx + xa, (yy + ya) + (zz + za));
    }

    private void move(double x, double y) {
        if (collision(x, y)) {
            this.xa *= -0.5;
            this.ya *= -0.5;
            this.za *= -0.5;
        }
        this.xx += xa;
        this.yy += ya;
        this.zz += za;
    }

    public boolean collision(double x, double y) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            double xt = (x - c % 2 * 16) / 16;
            double yt = (y - c / 2 * 16) / 16;
            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);
            if (c % 2 == 0) ix = (int) Math.floor(xt);
            if (c / 2 == 0) iy = (int) Math.floor(yt);
            if (level.getTile(ix, iy).solid()) solid = true;

        }
        return solid;
    }

    public void render(Screen screen) {
        screen.renderSprite((int) xx + 1, (int) yy - (int) zz, sprite, true);
    }

}
