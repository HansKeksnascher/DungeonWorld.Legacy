package diansa.dw.entity.projectile;

import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class BulletProj extends Projectile {

    public BulletProj(double x, double y, double dir) {
        super(x, y, dir);
        sprite = Sprite.rotate(Sprite.double_bullet, angle);
        range = 300;
        speed = 8;
        damage = Player.getPlayerStats(Stats.DAMAGE);

        // TODO: sprite

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    protected void move() {
        x += nx;
        y += ny;
        if (distance() > range) remove();
    }

    private double distance() {
        double dist = 0;
        dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
        return dist;
    }

    public void update() {
        checkCollision();
        move();
    }

    public void render(Screen screen) {
        screen.renderProjectile((int) (x - 8), (int) (y - 8), this);
    }
}
