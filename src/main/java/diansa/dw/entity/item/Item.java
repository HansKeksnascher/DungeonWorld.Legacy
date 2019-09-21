package diansa.dw.entity.item;

import diansa.dw.Game;
import diansa.dw.entity.Entity;
import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class Item extends Entity {

    public int x;
    public int y;
    protected Sprite sprite;
    public Sprite dropSprite;
    public Sprite icon;
    public boolean isEquipped = false;
    private boolean isSold = false;
    public int value = 0;
    public int tier = 1;
    // === ITEM STATS ======
    protected int health;
    protected int energy;
    protected int healthRegen;
    protected int energyRegen;
    protected int damage;
    protected int criticalHit;
    protected int criticalDamage;
    protected int attackSpeed;
    protected int movementSpeed;
    protected int luck;

    public String[] statName = new String[3];
    public int[] statValue = new int[3];

    protected int stats[] = new int[6];

    public enum ItemType {
        NONE, ARMOR, WEAPON, NECK, RING, GLOVES, BOOTS, KEY
    }

    public enum ItemRarity {
        NORMAL, MAGIC, RARE, UNIQUE;

        public static ItemRarity getRandomRarity() {
            return values()[(int) (Math.random() * (values().length))];
        }
    }

    public ItemType itemType = ItemType.NONE;
    public ItemRarity itemRarity = ItemRarity.NORMAL;

    public Item() {

    }

    protected void initStats() {
        for (int i = 0; i < 6; i++) {
            stats[i] = 0;
        }
    }

    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public boolean isSold() {
        return isSold;
    }

    public void sold() {
        isSold = true;
    }

    public int[] getStats(int[] stats) {
        return stats;
    }

    public void equip() {
        isEquipped = true;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void update() {
        for (int i = 0; i < level.players.size(); i++) {
            if (x < level.players.get(i).getX() + 16 && x > level.players.get(i).getX() - 16 && y < level.players.get(i).getY() + 16 && y > level.players.get(i).getY() - 16) {
                Player.getPlayerInventory().addItemToSlot(this);
                if (Player.getPlayerInventory().inventoryIsFull) {
                    return;
                } else {
                    Game.sounds[2].play();
                    remove();
                    return;
                }
            }
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
                    break;
                }
                if (firstStat == 1) {
                    statValue[0] = energy;
                    stats[0] = 0;
                    stats[1] = energy;
                    statName[0] = "EN: ";
                    break;
                }
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
                break;
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
        }

        return stats;
    }

    public void render(Screen screen) {

    }

    public void renderIcon(Screen screen, int xp, int yp) {

    }
}
