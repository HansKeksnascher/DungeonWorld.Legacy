package diansa.dw.entity.item;

import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.level.Level;

public class Armor extends Item {

    public Armor(int x, int y, ItemRarity rarity) {
        super(x, y);
        tier = 1 + (int) Math.ceil(Level.worldLevel / 10);
        itemRarity = rarity;
        itemType = ItemType.ARMOR;
        dropSprite = Sprite.armor;

        health = (int) (tier * 10 + random.nextInt(3) * 10 * tier + tier * 10 * (0.2 + (double) (Level.worldLevel * 0.1)));
        energy = (int) (tier * 5 + random.nextInt(3) * 5 * tier + tier * 5 * (0.2 + (double) (Level.worldLevel * 0.1)));
        energyRegen = (int) (Player.maxEnergy * (0.01 + 0.01 * tier));
        healthRegen = (int) (Player.maxHealth * (0.01));
        luck = 10;
        criticalDamage = 20 * tier + random.nextInt(3) * 5 * tier;

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
                sprite = Sprite.armor_normal;
                icon = Sprite.armor_icon_normal;
                break;
            case MAGIC:
                sprite = Sprite.armor_magic;
                icon = Sprite.armor_icon_magic;
                break;
            case RARE:
                sprite = Sprite.armor_rare;
                icon = Sprite.armor_icon_rare;
                break;
            case UNIQUE:
                sprite = Sprite.armor_unique;
                icon = Sprite.armor_icon_unique;
                break;
            default:
                sprite = Sprite.armor;
                break;
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
                statValue[2] = 0;
                statName[2] = "";

                return stats;
            case MAGIC:

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
                    statValue[1] = energyRegen;
                    stats[2] = energyRegen;
                    stats[3] = 0;
                    statName[1] = "E.REG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = healthRegen;
                    stats[2] = 0;
                    stats[3] = healthRegen;
                    statName[1] = "H.REG: ";
                }
                statValue[2] = 0;
                statName[2] = "";
                return stats;
            case RARE:
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
                    statValue[1] = energyRegen;
                    stats[2] = energyRegen;
                    stats[3] = 0;
                    statName[1] = "E.REG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = healthRegen;
                    stats[2] = 0;
                    stats[3] = healthRegen;
                    statName[1] = "H.REG: ";
                }

                // THIRD STAT
                if (thirdStat == 0) {
                    statValue[2] = luck;
                    stats[4] = luck;
                    stats[5] = 0;
                    statName[2] = "LUCK: ";
                }
                if (thirdStat == 1) {
                    statValue[2] = criticalDamage;
                    stats[4] = 0;
                    stats[5] = criticalDamage;
                    statName[2] = "C.DMG: ";
                }
                return stats;
            case UNIQUE:
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
                    statValue[1] = energyRegen;
                    stats[2] = energyRegen;
                    stats[3] = 0;
                    statName[1] = "E.REG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = healthRegen;
                    stats[2] = 0;
                    stats[3] = healthRegen;
                    statName[1] = "H.REG: ";
                }

                // THIRD STAT
                if (thirdStat == 0) {
                    statValue[2] = luck;
                    stats[4] = luck;
                    stats[5] = 0;
                    statName[2] = "LUCK: ";
                }
                if (thirdStat == 1) {
                    statValue[2] = criticalDamage;
                    stats[4] = 0;
                    stats[5] = criticalDamage;
                    statName[2] = "C.DMG: ";
                }
                return stats;
        }

        return stats;
    }

    public int[] getStats(int[] stats) {
        stats[18] = this.stats[0];
        stats[19] = this.stats[1];
        stats[20] = this.stats[2];
        stats[21] = this.stats[3];
        stats[22] = this.stats[4];
        stats[23] = this.stats[5];
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
