package diansa.dw.graphics.playerUI.inventory;

import diansa.dw.Game;
import diansa.dw.entity.item.Item;
import diansa.dw.entity.item.Item.ItemRarity;
import diansa.dw.graphics.Screen;
import diansa.dw.input.Mouse;

public class InventorySlot {

    int x, y;
    @SuppressWarnings("unused")
    private int width, height;
    protected Item itemInSlot;
    protected int slotIndex;
    protected boolean hasItem = false;
    protected static int[] tooltipStats = new int[36];

    public InventorySlot(int index, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        slotIndex = index;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public void addItem(Item item) {
        itemInSlot = item;
        hasItem = true;
    }

    public void removeItem() {
        hasItem = false;
        itemInSlot = null;
    }

    public boolean contains(double x, double y) {
        if (this.x < x + 8 && this.x > x - 8 && this.y < y + 8 && this.y > y - 8) {
            return true;
        }
        return false;
    }

    protected void renderTooltip(Screen screen) {
        if (this.slotIndex % 6 > 2)
            screen.renderSprite(Mouse.getX() / Game.scale - 100, Mouse.getY() / Game.scale, Inventory.tooltip, false);
        else
            screen.renderSprite(Mouse.getX() / Game.scale, Mouse.getY() / Game.scale, Inventory.tooltip, false);
        // HERE WE GO: A BIG PILE OF SHIT
        if (this.hasItem() && this.contains(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8)) {
            this.itemInSlot.getStats(tooltipStats);
            if (this.hasItem() && itemInSlot.itemRarity == ItemRarity.NORMAL) {
                if (this.slotIndex % 6 > 2)
                    Inventory.font.render(Mouse.getX() / Game.scale - 95, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0], screen);
                else
                    Inventory.font.render(Mouse.getX() / Game.scale + 5, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0], screen);

            }
            if (this.hasItem() && itemInSlot.itemRarity == ItemRarity.MAGIC) {
                if (this.slotIndex % 6 > 2)
                    Inventory.font.render(Mouse.getX() / Game.scale - 95, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1], screen);
                else
                    Inventory.font.render(Mouse.getX() / Game.scale + 5, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1], screen);

            }
            if (this.hasItem() && itemInSlot.itemRarity == ItemRarity.RARE || itemInSlot.itemRarity == ItemRarity.UNIQUE) {
                if (this.slotIndex % 6 > 2)
                    Inventory.font.render(Mouse.getX() / Game.scale - 95, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1] + "\n" + itemInSlot.statName[2] + itemInSlot.statValue[2], screen);
                else
                    Inventory.font.render(Mouse.getX() / Game.scale + 5, Mouse.getY() / Game.scale + 20, itemInSlot.statName[0] + itemInSlot.statValue[0] + "\n" + itemInSlot.statName[1] + itemInSlot.statValue[1] + "\n" + itemInSlot.statName[2] + itemInSlot.statValue[2], screen);

            }
        }
    }

}
