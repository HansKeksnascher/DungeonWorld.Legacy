package diansa.dw.entity;

import diansa.dw.Game;
import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.level.Level;

import java.util.Random;

public class Entity {

    protected double x, y; // mob, player location on the map
    protected static int world;
    protected Level level;
    protected final Random random = new Random();
    protected static boolean actionPerformed = false;
    protected static boolean shouldTeleport = false;
    protected int mobHealth = 100;
    protected int mobMaxHealth = 100;
    protected boolean hitted = false;
    protected boolean damaged = false;
    protected static int classID = 0; // 0 = default || 1 = gunslinger || 2 =
    // archer || 3 = flamethrower

    private boolean removed = false;

    protected enum Stats {
        CRITICAL_RATE, CRITICAL_DAMAGE, ATTACK_SPEED, DAMAGE;
    }

    protected Stats stats;

    public void damageMob(int damage) {
        if (!damaged) {
            if ((Math.random() * 100) < Player.getPlayerStats(Stats.CRITICAL_RATE)) {
                mobHealth -= (damage + damage * ((double) Player.getPlayerStats(Stats.CRITICAL_DAMAGE) / 100));
                System.out.println("Crit: " + (damage + damage * ((double) Player.getPlayerStats(Stats.CRITICAL_DAMAGE) / 100)));
            } else {
                mobHealth -= damage;
            }
            hitted = false;
        }
    }

    public void damagePlayer(int damage) {
        if (!damaged) {
            Player.playerHealth -= damage;
            damaged = true;
        }
    }

    public void hitted() {
        Game.sounds[0].play();
        hitted = true;
    }

    public void update() {
    }

    public void render(Screen screen) {
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void init(Level level) {
        this.level = level;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
