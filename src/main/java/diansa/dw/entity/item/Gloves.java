package diansa.dw.entity.item;

import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.level.Level;

public class Gloves extends Item {

    public Gloves(int x, int y, ItemRarity rarity) {
        super(x, y);
        tier = 1 + (int) Math.ceil(Level.worldLevel / 10);
        itemRarity = rarity;
        itemType = ItemType.GLOVES;
        dropSprite = Sprite.gloves;

        health = (int) (tier * 5 + random.nextInt(3) * 5 * tier + tier * 5 * (0.2 + (double) (Level.worldLevel * 0.1)));
        energy = 10;
        damage = 10;
        criticalHit = 10;
        attackSpeed = 1 * tier + Level.worldLevel;
        criticalDamage = 10;

        value = 5 + random.nextInt(3) * 10 * getRarityMulti();
        statName = new String[3];
        statValue = new int[3];
        initStats();
        setSprite();
        setItemStats(stats);

    }

    protected void setSprite() {
        switch (itemRarity) {
            case NORMAL:
                sprite = Sprite.gloves_normal;
                icon = Sprite.gloves_icon_normal;
                break;
            case MAGIC:
                sprite = Sprite.gloves_magic;
                icon = Sprite.gloves_icon_magic;
                break;
            case RARE:
                sprite = Sprite.gloves_rare;
                icon = Sprite.gloves_icon_rare;
                break;
            case UNIQUE:
                sprite = Sprite.gloves_unique;
                icon = Sprite.gloves_icon_unique;
                break;
            default:
                sprite = Sprite.gloves;
        }
    }

    protected int getRarityMulti() {
        switch (itemRarity) {
            case NORMAL:
                return 1;
            case MAGIC:
                return 2;
            case RARE:
                return 3;
            case UNIQUE:
                return 4;
            default:
                return 1;
        }
    }

    public int[] setItemStats(int[] stats) {
        int firstStat = random.nextInt(2);
        int secondStat = random.nextInt(2);
        int thirdStat = random.nextInt(2);

        switch (itemRarity) {
            case NORMAL:
                // FIRST STAT
                if (firstStat == 0) {
                    statValue[0] = health;
                    stats[0] = health;
                    stats[1] = 0;
                    statName[0] = "HP: ";
                }
                if (firstStat == 1) {
                    statValue[0] = energy;
                    stats[0] = 0;
                    stats[1] = energy;
                    statName[0] = "EN: ";
                }
                statValue[1] = 0;
                statName[1] = "";
                return stats;
            case MAGIC:
                // FIRST STAT
                if (firstStat == 0) {
                    statValue[0] = health;
                    stats[0] = health;
                    stats[1] = 0;
                    statName[0] = "HP: ";
                }
                if (firstStat == 1) {
                    statValue[0] = energy;
                    stats[0] = 0;
                    stats[1] = energy;
                    statName[0] = "EN: ";
                }

                // SECOND STAT
                if (secondStat == 0) {
                    statValue[1] = damage;
                    stats[2] = damage;
                    stats[3] = 0;
                    statName[1] = "DMG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = criticalHit;
                    stats[2] = 0;
                    stats[3] = criticalHit;
                    statName[1] = "C.HIT ";
                }
                statValue[2] = 0;
                statName[2] = "";
                return stats;
            case RARE:
                // FIRST STAT
                if (firstStat == 0) {
                    statValue[0] = health;
                    stats[0] = health;
                    stats[1] = 0;
                    statName[0] = "HP: ";
                }
                if (firstStat == 1) {
                    statValue[0] = energy;
                    stats[0] = 0;
                    stats[1] = energy;
                    statName[0] = "EN: ";
                }

                // SECOND STAT
                if (secondStat == 0) {
                    statValue[1] = damage;
                    stats[2] = damage;
                    stats[3] = 0;
                    statName[1] = "DMG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = criticalHit;
                    stats[2] = 0;
                    stats[3] = criticalHit;
                    statName[1] = "C.HIT ";
                }

                // THIRD STAT
                if (thirdStat == 0) {
                    statValue[2] = attackSpeed;
                    stats[4] = attackSpeed;
                    stats[5] = 0;
                    statName[2] = "A.SPD ";
                }
                if (thirdStat == 1) {
                    statValue[2] = criticalDamage;
                    stats[4] = 0;
                    stats[5] = criticalDamage;
                    statName[2] = "C.DMG ";
                }
                return stats;
            case UNIQUE:
                // FIRST STAT
                if (firstStat == 0) {
                    statValue[0] = health;
                    stats[0] = health;
                    stats[1] = 0;
                    statName[0] = "HP: ";
                }
                if (firstStat == 1) {
                    statValue[0] = energy;
                    stats[0] = 0;
                    stats[1] = energy;
                    statName[0] = "EN: ";
                }

                // SECOND STAT
                if (secondStat == 0) {
                    statValue[1] = damage;
                    stats[2] = damage;
                    stats[3] = 0;
                    statName[1] = "DMG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = criticalHit;
                    stats[2] = 0;
                    stats[3] = criticalHit;
                    statName[1] = "C.HIT ";
                }

                // THIRD STAT
                if (thirdStat == 0) {
                    statValue[2] = attackSpeed;
                    stats[4] = attackSpeed;
                    stats[5] = 0;
                    statName[2] = "A.SPD ";
                }
                if (thirdStat == 1) {
                    statValue[2] = criticalDamage;
                    stats[4] = 0;
                    stats[5] = criticalDamage;
                    statName[2] = "C.DMG ";
                }
                return stats;
        }
        return stats;
    }

    public int[] getStats(int[] stats) {
        stats[24] = this.stats[0];
        stats[25] = this.stats[1];
        stats[26] = this.stats[2];
        stats[27] = this.stats[3];
        stats[28] = this.stats[4];
        stats[29] = this.stats[5];
        return stats;
    }

    public void update() {
        super.update();
    }

    public void render(Screen screen) {
        screen.renderMob(x - 16, y - 16, dropSprite, 32);
        if (this.itemRarity == ItemRarity.NORMAL) {
            screen.renderSprite(x - 16, y - 30, SpriteSheet.drop_frame_normal, true);
        } else if (this.itemRarity == ItemRarity.MAGIC) {
            screen.renderSprite(x - 16, y - 30, SpriteSheet.drop_frame_rare, true);
        } else if (this.itemRarity == ItemRarity.RARE) {
            screen.renderSprite(x - 16, y - 30, SpriteSheet.drop_frame_epic, true);
        } else {
            screen.renderSprite(x - 16, y - 30, SpriteSheet.drop_frame_legend, true);
        }
    }

    public void renderIcon(Screen screen, int xp, int yp) {
        screen.renderSprite(xp, yp, sprite, false);
    }
}
