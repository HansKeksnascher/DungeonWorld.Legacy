package diansa.dw.entity.projectile;

import diansa.dw.entity.player.Player;
import diansa.dw.entity.spawner.ParticleSpawner;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class MobProj extends Projectile {

    public MobProj(double x, double y, double dir) {
        super(x, y, dir);
        range = 350;
        speed = 4;
        damage = 10 + 10 * Player.getPlayerLevel();
        sprite = Sprite.rotate(Sprite.double_bullet, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    public void update() {
        if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 8, 8)) {
            remove();
        }

        for (int i = 0; i < level.players.size(); i++) {
            if (x < level.players.get(i).getX() + 10 && x > level.players.get(i).getX() - 10 && y < level.players.get(i).getY() + 16 && y > level.players.get(i).getY() - 2) {
                remove();
                level.players.get(i).damagePlayer(damage);
                level.players.get(i).hitted();
                level.add(new ParticleSpawner((int) x, (int) y, 15, 60, level, Sprite.red_particle));

            }
        }
        move();
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

    public void render(Screen screen) {
        screen.renderProjectile((int) (x - 8), (int) (y - 8), this);
    }
}
