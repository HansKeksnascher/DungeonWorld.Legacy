package diansa.dw.entity.projectile;

import diansa.dw.entity.Entity;
import diansa.dw.entity.spawner.ParticleSpawner;
import diansa.dw.graphics.Sprite;

public class Projectile extends Entity {

    final protected int xOrigin, yOrigin;
    protected double angle;
    protected Sprite sprite;
    protected double x, y;
    protected double nx, ny;
    protected double distance;
    protected double range;
    protected int damage;
    protected double speed;

    public Projectile(double x, double y, double dir) {
        xOrigin = (int) x;
        yOrigin = (int) y;
        angle = dir;
        this.x = x;
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getSpriteSize() {
        return sprite.SIZE;
    }

    public double setSpeed(double speed) {
        this.speed = speed;
        return this.speed;
    }

    public double getAngle() {
        return angle;
    }

    protected void move() {
    }

    protected void checkCollision() {
        if (level.tileCollision((int) (x + nx), (int) (y + ny), 1, 1, 1)) {
            remove();
        }
        for (int i = 0; i < level.enemies.size(); i++) {
            if (x < level.enemies.get(i).getX() + 10 && x > level.enemies.get(i).getX() - 10
                    && y < level.enemies.get(i).getY() + 16 && y > level.enemies.get(i).getY() - 16) {
                level.enemies.get(i).damageMob(damage);
                level.enemies.get(i).hitted();
                level.add(new ParticleSpawner((int) x, (int) y, 10, 50, level, Sprite.red_particle));

                remove();
                return;
            }
        }
    }

}
