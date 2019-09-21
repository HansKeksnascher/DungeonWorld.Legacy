package diansa.dw.entity.mob;

import diansa.dw.Game;
import diansa.dw.entity.Entity;
import diansa.dw.entity.item.Armor;
import diansa.dw.entity.item.Boots;
import diansa.dw.entity.item.Gloves;
import diansa.dw.entity.item.Item.ItemRarity;
import diansa.dw.entity.item.KeyItem;
import diansa.dw.entity.item.KeyItem.KeyType;
import diansa.dw.entity.item.Pistol;
import diansa.dw.entity.player.Player;
import diansa.dw.entity.projectile.BulletProj;
import diansa.dw.entity.projectile.MobProj;
import diansa.dw.entity.projectile.Projectile;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class Mob extends Entity {

    protected Sprite sprite;
    protected boolean moving = false;
    protected boolean walking = false;
    private static int keycounter = 0;
    private static int mobsKilled = 0;
    private static int normalItems = 0;
    private static int magicItems = 0;
    private static int rareItems = 0;
    private static int uniqueItems = 0;
    protected int aggroRange = 150;

    protected enum Direction {
        UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, UP, DOWN, LEFT, RIGHT;
    }

    protected Direction direction;

    public void move(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }

        while (xa != 0) {
            if (Math.abs(xa) > 1) {
                if (!collision(abs(xa), ya)) {
                    this.x += abs(xa);
                }
                xa -= abs(xa);
            } else {
                if (!collision(abs(xa), ya)) {
                    this.x += xa;
                }
                xa = 0;
            }
        }

        while (ya != 0) {
            if (Math.abs(ya) > 1) {
                if (!collision(xa, abs(ya))) {
                    this.y += abs(ya);
                }
                ya -= abs(ya);
            } else {
                if (!collision(xa, abs(ya))) {
                    this.y += ya;
                }
                ya = 0;
            }
        }

    }

    private int abs(double value) {
        if (value < 0) return -1;
        return 1;
    }

    protected void shoot(double x, double y, double dir) {
        Projectile p = new BulletProj(x, y, dir);
        Game.sounds[1].play();
        level.add(p);
    }

    protected void mobShoot(double x, double y, double dir, int proj) {
        switch (proj) {
            case 0:
                Projectile p = new MobProj(x, y, dir);
                level.add(p);
                break;
        }
    }

    protected void bossDrop() {
        int drop = random.nextInt(100);
        if (drop >= 70 - Player.luck) {
            level.add(new Pistol((int) x, (int) y, ItemRarity.UNIQUE));
            uniqueItems++;
            Game.sounds[3].play();
            return;
        }
        if (drop > 40 - Player.luck) {
            level.add(new Pistol((int) x, (int) y, ItemRarity.RARE));
            rareItems++;
            return;
        }
        if (drop >= 0) {
            level.add(new Pistol((int) x, (int) y, ItemRarity.MAGIC));
            magicItems++;
            return;
        }
    }

    protected void dropLoot() {
        int drop = random.nextInt(6);
        int drop2 = random.nextInt(200);
        mobsKilled++;
        System.out.println("Keys dropt: " + keycounter + "Mobs killed: " + mobsKilled + "\n" + "Normal Items: " + normalItems + " Magic Items: " + magicItems + " Rare Items: " + rareItems + " Unique Items: " + uniqueItems);
        switch (drop) {
            case 0:
                if (drop2 == 0 || drop2 == 199) {
                    level.add(new Gloves((int) x, (int) y, ItemRarity.UNIQUE));
                    uniqueItems++;
                    Game.sounds[3].play();
                    break;

                }
                if (drop2 > 170 - Player.luck) {
                    level.add(new Gloves((int) x, (int) y, ItemRarity.RARE));
                    rareItems++;
                    break;

                }
                if (drop2 > 90 - Player.luck) {
                    level.add(new Gloves((int) x, (int) y, ItemRarity.MAGIC));
                    magicItems++;
                    break;
                }
                if (drop2 > 0) {
                    level.add(new Gloves((int) x, (int) y, ItemRarity.NORMAL));
                    normalItems++;
                    break;
                }
                break;
            case 1:
                if (drop2 == 0 || drop2 == 199) {
                    level.add(new Boots((int) x, (int) y, ItemRarity.UNIQUE));
                    uniqueItems++;
                    Game.sounds[3].play();
                    break;

                }
                if (drop2 > 170 - Player.luck) {
                    level.add(new Boots((int) x, (int) y, ItemRarity.RARE));
                    rareItems++;
                    break;

                }
                if (drop2 > 90 - Player.luck) {
                    level.add(new Boots((int) x, (int) y, ItemRarity.MAGIC));
                    magicItems++;
                    break;
                }
                if (drop2 > 0) {
                    level.add(new Boots((int) x, (int) y, ItemRarity.NORMAL));
                    normalItems++;
                    break;
                }
                break;
            case 2:
                if (drop2 == 0 || drop2 == 199) {
                    level.add(new Armor((int) x, (int) y, ItemRarity.UNIQUE));
                    uniqueItems++;
                    Game.sounds[3].play();
                    break;

                }
                if (drop2 > 170 - Player.luck) {
                    level.add(new Armor((int) x, (int) y, ItemRarity.RARE));
                    rareItems++;
                    break;

                }
                if (drop2 > 90 - Player.luck) {
                    level.add(new Armor((int) x, (int) y, ItemRarity.MAGIC));
                    magicItems++;
                    break;
                }
                if (drop2 > 0) {
                    level.add(new Armor((int) x, (int) y, ItemRarity.NORMAL));
                    normalItems++;
                    break;
                }
                break;
            case 3:
                int drop3 = random.nextInt(10);
                if (drop3 > 6) {
                    keycounter++;
                    KeyItem key = new KeyItem((int) x, (int) y, KeyType.TREASURE_ROOM);
                    level.add(key);
                    Game.sounds[3].play();
                    System.out.println("Keys dropt: " + keycounter);
                }

                break;
            default:
                break;
        }
    }


    private boolean collision(double xa, double ya) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            double xt = ((x + xa) - c % 2 * 4 - 5) / 16;
            double yt = ((y + ya) - c / 2 * 2 - 0) / 16; // optimal * 14 + 12 // lagfree *2 - 0
            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);
            if (c % 2 == 0) ix = (int) Math.floor(xt);
            if (c / 2 == 0) iy = (int) Math.floor(yt);
            if (level.getTile(ix, iy).solid()) solid = true;
        }
        return solid;
    }

    public void update() {
        if (hitted) {
            if (damaged) {
                hitted = false;
                damaged = false;
                aggroRange = 350;
            }
        }
    }

    public void render(Screen screen) {
    }
}
