package diansa.dw.entity.item;

import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.level.Level;

public class Pistol extends Item {

    public Pistol(ItemRarity rarity, int damage) {
        tier = 1 + (int) Math.ceil(Level.worldLevel / 10);
        itemType = ItemType.WEAPON;
        itemRarity = rarity;
        dropSprite = Sprite.pistol;
        this.damage = damage;
        criticalHit = tier * 20 + random.nextInt(3) * 5 * tier;
        energyRegen = (int) Math.floor(Player.maxEnergy * (0.05 + tier / 100));
        attackSpeed = 10;
        criticalDamage = 10;
        value = 5 + random.nextInt(3) * 10;
        statName = new String[3];
        statValue = new int[3];
        initStats();
        setSprite();
        setItemStats(stats);
    }

    public Pistol(int x, int y, ItemRarity rarity) {
        super(x, y);
        itemRarity = rarity;
        itemType = ItemType.WEAPON;

        dropSprite = Sprite.pistol;
        tier = 1 + (int) Math.ceil(Level.worldLevel / 10);

        damage = (int) (tier * 10 + random.nextInt(3) * 10 * tier + tier * 10 * (0.2 + (double) (Level.worldLevel * 0.1)));
        criticalHit = tier * 20 + random.nextInt(3) * 5 * tier;
        criticalDamage = 20 * tier + random.nextInt(3) * 5 * tier;
        attackSpeed = 1 * tier + Level.worldLevel;
        energyRegen = 10 * tier;

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
                sprite = Sprite.pistol_normal;
                icon = Sprite.pistol_icon_normal;
                break;
            case MAGIC:
                sprite = Sprite.pistol_magic;
                icon = Sprite.pistol_icon_magic;
                break;
            case RARE:
                sprite = Sprite.pistol_rare;
                icon = Sprite.pistol_icon_rare;
                break;
            case UNIQUE:
                sprite = Sprite.pistol_unique;
                icon = Sprite.pistol_icon_unique;
                break;
            default:
                sprite = Sprite.pistol;
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
        int secondStat = random.nextInt(2);
        int thirdStat = random.nextInt(2);

        switch (itemRarity) {
            case NORMAL:
                // FIRST STAT
                statValue[0] = damage;
                stats[0] = damage;
                statName[0] = "DMG: ";

                statValue[1] = 0;
                statName[1] = "";
                statValue[2] = 0;
                statName[2] = "";
                return stats;
            case MAGIC:
                // FIRST STAT
                statValue[0] = damage;
                stats[0] = damage;
                statName[0] = "DMG: ";

                // SECOND STAT
                if (secondStat == 0) {
                    statValue[1] = criticalDamage;
                    stats[1] = criticalDamage;
                    stats[2] = 0;
                    statName[1] = "C.DMG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = criticalHit;
                    stats[1] = 0;
                    stats[2] = criticalHit;
                    statName[1] = "C.HIT: ";
                }
                statValue[2] = 0;
                statName[2] = "";
                return stats;
            case RARE:
                // FIRST STAT
                statValue[0] = damage;
                stats[0] = damage;
                statName[0] = "DMG: ";

                // SECOND STAT
                if (secondStat == 0) {
                    statValue[1] = criticalDamage;
                    stats[1] = criticalDamage;
                    stats[2] = 0;
                    statName[1] = "C.DMG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = criticalHit;
                    stats[1] = 0;
                    stats[2] = criticalHit;
                    statName[1] = "C.HIT: ";
                }

                // THIRD STAT
                if (thirdStat == 0) {
                    statValue[2] = attackSpeed;
                    stats[3] = attackSpeed;
                    stats[4] = 0;
                    statName[2] = "A.SPD: ";
                }
                if (thirdStat == 1) {
                    statValue[2] = energyRegen;
                    stats[3] = 0;
                    stats[4] = energyRegen;
                    statName[2] = "E.REG: ";
                }
                return stats;
            case UNIQUE:
                // FIRST STAT
                statValue[0] = damage;
                stats[0] = damage;
                statName[0] = "DMG: ";

                // SECOND STAT
                if (secondStat == 0) {
                    statValue[1] = criticalDamage;
                    stats[1] = criticalDamage;
                    stats[2] = 0;
                    statName[1] = "C.DMG: ";
                }
                if (secondStat == 1) {
                    statValue[1] = criticalHit;
                    stats[1] = 0;
                    stats[2] = criticalHit;
                    statName[1] = "C.HIT: ";
                }

                // THIRD STAT
                if (thirdStat == 0) {
                    statValue[2] = attackSpeed;
                    stats[3] = attackSpeed;
                    stats[4] = 0;
                    statName[2] = "A.SPD: ";
                }
                if (thirdStat == 1) {
                    statValue[2] = energyRegen;
                    stats[3] = 0;
                    stats[4] = energyRegen;
                    statName[2] = "E.REG: ";
                }
                return stats;
        }
        return stats;
    }

    public int[] getStats(int[] stats) {
        stats[0] = this.stats[0];
        stats[1] = this.stats[1];
        stats[2] = this.stats[2];
        stats[3] = this.stats[3];
        stats[4] = this.stats[4];
        stats[5] = this.stats[5];
        return stats;
    }

    public boolean isEquipped() {
        return isEquipped;
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
