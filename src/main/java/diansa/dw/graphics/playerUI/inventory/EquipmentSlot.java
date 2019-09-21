package diansa.dw.graphics.playerUI.inventory;

import diansa.dw.Game;
import diansa.dw.entity.item.Item;
import diansa.dw.entity.item.Item.ItemRarity;
import diansa.dw.entity.item.Item.ItemType;
import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.input.Mouse;

public class EquipmentSlot {

    protected int x, y, width, height, slotIndex;
    protected Item itemInSlot;
    private boolean hasItem = false;
    protected static int[] tooltipStats = new int[36];

    protected enum EquipmentSlotType {
        WEAPON_SLOT, ARMOR_SLOT, GLOVES_SLOT, BOOTS_SLOT, RING_SLOT, NECK_SLOT
    }

    protected ItemType slotType;

    public EquipmentSlot(ItemType slotType, int slotIndex, int x, int y, int width, int height) {
        this.slotType = slotType;
        this.slotIndex = slotIndex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public boolean hasItem() {
        return hasItem;
    }

    public void addItem(Item item) {
        itemInSlot = item;
        hasItem = true;
    }

    public void removeItem() {
        itemInSlot = null;
        hasItem = false;
    }

    public boolean contains(int x, int y) {
        if (this.x < x + width / 2 && this.x > x - width / 2 && this.y < y + height / 2 && this.y > y - height / 2) {
            return true;
        } else {
            return false;
        }
    }

    public void getEquipmentStats() {
        if (itemInSlot == null) {
            switch (slotType) {
                case WEAPON:
                    Player.equipmentStats[0] = 0;
                    Player.equipmentStats[1] = 0;
                    Player.equipmentStats[2] = 0;
                    Player.equipmentStats[3] = 0;
                    Player.equipmentStats[4] = 0;
                    Player.equipmentStats[5] = 0;
                    Player.multiShot = false;
                    break;
                case ARMOR:
                    Player.equipmentStats[18] = 0;
                    Player.equipmentStats[19] = 0;
                    Player.equipmentStats[20] = 0;
                    Player.equipmentStats[21] = 0;
                    Player.equipmentStats[22] = 0;
                    Player.equipmentStats[23] = 0;
                    break;
                case NECK:
                    Player.equipmentStats[6] = 0;
                    Player.equipmentStats[7] = 0;
                    Player.equipmentStats[8] = 0;
                    Player.equipmentStats[9] = 0;
                    Player.equipmentStats[10] = 0;
                    Player.equipmentStats[11] = 0;
                    break;
                case RING:
                    Player.equipmentStats[12] = 0;
                    Player.equipmentStats[13] = 0;
                    Player.equipmentStats[14] = 0;
                    Player.equipmentStats[15] = 0;
                    Player.equipmentStats[16] = 0;
                    Player.equipmentStats[17] = 0;
                    break;
                case GLOVES:
                    Player.equipmentStats[24] = 0;
                    Player.equipmentStats[25] = 0;
                    Player.equipmentStats[26] = 0;
                    Player.equipmentStats[27] = 0;
                    Player.equipmentStats[28] = 0;
                    Player.equipmentStats[29] = 0;
                    break;
                case BOOTS:
                    Player.equipmentStats[30] = 0;
                    Player.equipmentStats[31] = 0;
                    Player.equipmentStats[32] = 0;
                    Player.equipmentStats[33] = 0;
                    Player.equipmentStats[34] = 0;
                    Player.equipmentStats[35] = 0;
                    break;
                default:
                    break;
            }
        } else {
            itemInSlot.getStats(Player.equipmentStats);
            if (itemInSlot.itemType == ItemType.WEAPON && itemInSlot.itemRarity == ItemRarity.UNIQUE) {
                Player.multiShot = true;
            } else {
                if (itemInSlot.itemType == ItemType.WEAPON && itemInSlot.itemRarity != ItemRarity.UNIQUE)
                    Player.multiShot = false;
            }
        }
        // itemInSlot = Player.equipment.get(slotIndex);
        // if(itemInSlot != null)itemInSlot.getStats(Player.equipmentStats);
        // System.out.println(Player.equipmentStats[slotIndex]);
    }

    protected void renderTooltip(Screen screen) {
        if (this.slotIndex == 1 || this.slotIndex == 5)
            screen.renderSprite(Mouse.getX() / Game.scale - 100, Mouse.getY() / Game.scale, Inventory.tooltip, false);
        else
            screen.renderSprite(Mouse.getX() / Game.scale, Mouse.getY() / Game.scale, Inventory.tooltip, false);
        // HERE WE GO: A BIG PILE OF SHIT

        if (this.hasItem() && this.contains(Mouse.getX() / Game.scale - 16, Mouse.getY() / Game.scale - 16)) {
            this.itemInSlot.getStats(tooltipStats);

            if (itemInSlot.itemRarity == ItemRarity.NORMAL) {
                if (this.slotIndex == 1 || this.slotIndex == 5)
                    Inventory.font.render(Mouse.getX() / Game.scale - 95, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0], screen);
                else
                    Inventory.font.render(Mouse.getX() / Game.scale + 5, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0], screen);

            }
            if (itemInSlot.itemRarity == ItemRarity.MAGIC) {
                if (this.slotIndex == 1 || this.slotIndex == 5)
                    Inventory.font.render(Mouse.getX() / Game.scale - 95, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1], screen);
                else
                    Inventory.font.render(Mouse.getX() / Game.scale + 5, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1], screen);

            }
            if (itemInSlot.itemRarity == ItemRarity.RARE || itemInSlot.itemRarity == ItemRarity.UNIQUE) {
                if (this.slotIndex == 1 || this.slotIndex == 5)
                    Inventory.font.render(Mouse.getX() / Game.scale - 95, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1] + "\n" + itemInSlot.statName[2] + itemInSlot.statValue[2], screen);
                else
                    Inventory.font.render(Mouse.getX() / Game.scale + 5, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1] + "\n" + itemInSlot.statName[2] + itemInSlot.statValue[2], screen);

            }
        }
    }

    public void render(Screen screen) {
        screen.drawRect(x, y, width, height, 0xff0000, false);
    }
}
