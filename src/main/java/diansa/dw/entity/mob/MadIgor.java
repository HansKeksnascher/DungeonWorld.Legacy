package diansa.dw.entity.mob;

import diansa.dw.entity.Entity;
import diansa.dw.entity.npc.Portal;
import diansa.dw.entity.player.Player;
import diansa.dw.graphics.AnimatedSprite;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.level.Node;
import diansa.dw.util.Vector2i;

import java.util.List;

public class MadIgor extends Mob {

    private AnimatedSprite stay_side = new AnimatedSprite(SpriteSheet.ft_stay_side, 32, 32, 3);
    // private AnimatedSprite stay_up = new
    // AnimatedSprite(SpriteSheet.archer_mob_stay_up, 32, 32, 3);
    // private AnimatedSprite stay_down = new
    // AnimatedSprite(SpriteSheet.archer_mob_stay_down, 32, 32, 3);
    // private AnimatedSprite stay_upside = new
    // AnimatedSprite(SpriteSheet.archer_mob_stay_upside, 32, 32, 3);

    private AnimatedSprite move_side = new AnimatedSprite(SpriteSheet.ft_move_side, 32, 32, 3);
    private AnimatedSprite move_up = new AnimatedSprite(SpriteSheet.ft_move_up, 32, 32, 3);
    private AnimatedSprite move_down = new AnimatedSprite(SpriteSheet.ft_move_down, 32, 32, 3);
    // private AnimatedSprite move_upside = new
    // AnimatedSprite(SpriteSheet.archer_mob_move_upside, 32, 32, 3);

    private AnimatedSprite animSprite = stay_side;

    private List<Node> path = null;
    private boolean playerInRange = false;

    private int time = 0;
    int xa = 0;
    int ya = 0;
    private int fireRate = 0;

    public MadIgor(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        mobHealth = 1000 * ((int) Math.ceil(Player.getPlayerLevel() / 10) + 1) + 30 * Player.getPlayerLevel();
        mobMaxHealth = mobHealth;
        sprite = animSprite;
    }

    private void move() {

        int px = (int) level.getPlayerAt(0).getX();
        int py = (int) level.getPlayerAt(0).getY();
        Vector2i start = new Vector2i((int) getX() >> 4, (int) getY() >> 4);
        Vector2i dest = new Vector2i(px >> 4, py >> 4);
        if (time % 10 == 0) path = level.findPath(start, dest);
        if (path != null) {
            if (path.size() > 10) {
                xa = 0;
                ya = 0;
                Vector2i vec = path.get(path.size() - 1).tile;
                if (x < vec.getX() << 4) {
                    xa++;
                    animSprite = move_side;
                }
                if (x > vec.getX() << 4) {
                    animSprite = move_side;
                    xa--;
                }
                if (y < vec.getY() << 4) {
                    animSprite = move_down;
                    ya++;
                }
                if (y > vec.getY() << 4) {
                    animSprite = move_up;
                    ya--;
                }
            } else {
                if (time % 60 == 0) {
                    System.out.println("Hab dich!!!");

                    if (ya < 0) {
                        animSprite = move_up;
                    }
                    if (ya > 0) {
                        animSprite = move_down;
                    }
                    if (xa < 0) {
                        animSprite = move_side;
                    }
                    if (xa > 0) {
                        animSprite = move_side;
                    }

                    xa = random.nextInt(3) - 1;
                    ya = random.nextInt(3) - 1;
                }
            }
        }

        if (xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    public void update() {
        super.update();
        if (time > 8000) time = 0;
        time++;
        if (fireRate > 0) fireRate--;

        if (mobHealth <= 0) {
            // TODO: set to 20 or whatever value is ok for level progress
            Player.playerLevelProgress += (20 * (Player.getPlayerLevel() * 0.2));
            bossDrop();
            level.add(new Portal((int) (x / 16), (int) (y / 16), 0));
            remove();
            return;
        }
        animSprite.update();
        // ===== MOB BEHAVIOR ===========================
        List<Mob> players = level.getPlayers(this, 320);
        double min = 0;
        Mob closest = null;
        for (int i = 0; i < players.size(); i++) {
            Entity e = players.get(i);
            double distance = Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) e.getX(), (int) e.getY()));
            if (i == 0 || distance < min) {
                min = distance;
                closest = (Player) e;
            }
        }
        if (closest != null) {
            if (fireRate <= 0) {
                double dx = (closest.getX() - x);
                double dy = (closest.getY() - y);
                double dir = Math.atan2(dy, dx);
                mobShoot(x, y, dir, 0);
                fireRate = 30;
            }
        }

        if (closest != null) {
            playerInRange = true;
            move();
        } else if (closest == null) {
            playerInRange = false;
        }
        if (hitted) {
            System.out.println(mobHealth);
            hitted = false;
        }
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
        if (playerInRange) {
            screen.renderHPBar(130, 10, 250, 10, mobHealth * 250 / mobMaxHealth, false);
        }
    }

}
