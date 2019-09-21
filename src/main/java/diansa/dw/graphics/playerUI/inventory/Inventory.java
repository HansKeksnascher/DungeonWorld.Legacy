package diansa.dw.graphics.playerUI.inventory;

import diansa.dw.Game;
import diansa.dw.entity.item.Item;
import diansa.dw.entity.item.Item.ItemType;
import diansa.dw.entity.npc.GeneralShopNPC;
import diansa.dw.entity.player.Player;
import diansa.dw.events.Event;
import diansa.dw.events.EventDispatcher;
import diansa.dw.events.types.MouseMovedEvent;
import diansa.dw.events.types.MousePressedEvent;
import diansa.dw.events.types.MouseReleasedEvent;
import diansa.dw.graphics.Font;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.graphics.layers.Layer;
import diansa.dw.input.Mouse;

import java.awt.event.MouseEvent;

public class Inventory extends Layer {

    private int px, py;
    private int[] xoInv, yoInv, xoEquip, yoEquip;
    private int startSlot, destinationSlot;
    private int equipSlot;
    private int slotIndex, slotIndexEquip;

    protected Player player;
    public static Font font;
    private InventorySlot[] inventorySlot;
    private EquipmentSlot[] equipmentSlot;
    private Item storage;

    private boolean dragging = false;
    private boolean draggingInventorySlot = false;
    private boolean draggingEquipmentSlot = false;
    @SuppressWarnings("unused")
    private boolean showTooltip = false;
    public boolean inventoryIsFull = false;

    protected static SpriteSheet tooltip;

    private enum SlotType {
        INVENTORY_SLOT, EQUIPMENT_SLOT
    }

    @SuppressWarnings("unused")
    private SlotType slotType;

    public Inventory(Player player) {
        this.player = player;
        tooltip = SpriteSheet.tooltipV2;
        inventorySlot = new InventorySlot[18];
        equipmentSlot = new EquipmentSlot[6];
        storage = new Item();
        font = new Font();
        initSlots(18, 6);

    }

    private void initSlots(int invSlots, int equipSlots) {
        xoInv = new int[invSlots];
        yoInv = new int[invSlots];
        xoEquip = new int[equipSlots];
        yoEquip = new int[equipSlots];

        for (int i = 0; i < invSlots; i++) {
            inventorySlot[i] = new InventorySlot(i, 296 + (i % 6) * 24, 183 + (i / 6) * 24, 16, 16);
            xoInv[i] = inventorySlot[i].x;
            yoInv[i] = inventorySlot[i].y;
        }

        equipmentSlot[0] = new EquipmentSlot(ItemType.WEAPON, 0, 297, 93, 32, 32);
        xoEquip[0] = equipmentSlot[0].x;
        yoEquip[0] = equipmentSlot[0].y;
        equipmentSlot[1] = new EquipmentSlot(ItemType.ARMOR, 1, 397, 93, 32, 32);
        xoEquip[1] = equipmentSlot[1].x;
        yoEquip[1] = equipmentSlot[1].y;
        equipmentSlot[2] = new EquipmentSlot(ItemType.NECK, 2, 347, 93, 32, 32);
        xoEquip[2] = equipmentSlot[2].x;
        yoEquip[2] = equipmentSlot[2].y;
        equipmentSlot[3] = new EquipmentSlot(ItemType.RING, 3, 347, 134, 32, 32);
        xoEquip[3] = equipmentSlot[3].x;
        yoEquip[3] = equipmentSlot[3].y;
        equipmentSlot[4] = new EquipmentSlot(ItemType.GLOVES, 4, 297, 134, 32, 32);
        xoEquip[4] = equipmentSlot[4].x;
        yoEquip[4] = equipmentSlot[4].y;
        equipmentSlot[5] = new EquipmentSlot(ItemType.BOOTS, 5, 397, 134, 32, 32);
        xoEquip[5] = equipmentSlot[5].x;
        yoEquip[5] = equipmentSlot[5].y;

    }

    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> (onMousePressed((MousePressedEvent) e)));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> (onMouseReleased((MouseReleasedEvent) e)));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> (onMouseMoved((MouseMovedEvent) e)));

    }

    private boolean onMousePressed(MousePressedEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            startSlot = setSlotIndex(SlotType.INVENTORY_SLOT);
            equipSlot = setSlotIndex(SlotType.EQUIPMENT_SLOT);
            System.out.println(startSlot + " start // equip" + equipSlot);
            if (containsMouse(SlotType.INVENTORY_SLOT)) {

                draggingInventorySlot = true;
                return draggingInventorySlot;

            }
            if (containsMouse(SlotType.EQUIPMENT_SLOT)) {

                draggingEquipmentSlot = true;
                return draggingEquipmentSlot;

            }
        }
        if (containsMouse(SlotType.INVENTORY_SLOT) && e.getButton() == MouseEvent.BUTTON2) {
            if (inventorySlot[slotIndex].hasItem) {
                inventorySlot[slotIndex].removeItem();
            }
        }

        if (containsMouse(SlotType.INVENTORY_SLOT) && e.getButton() == MouseEvent.BUTTON3 && GeneralShopNPC.shopIsOpen) {
            System.out.println("Item sold");
            if (inventorySlot[slotIndex].hasItem) {
                Player.addMoney(inventorySlot[slotIndex].itemInSlot.value);
                inventorySlot[slotIndex].removeItem();
                Game.sounds[5].play();

                return dragging;
            }
        }

        return dragging;
    }

    private boolean containsMouse(SlotType slotType) {
        if (slotType == SlotType.EQUIPMENT_SLOT) {

            if (equipmentSlot[equipSlot].contains(Mouse.getX() / Game.scale - 16, Mouse.getY() / Game.scale - 16)) {
                return true;
            } else {
                return false;
            }
        }
        if (slotType == SlotType.INVENTORY_SLOT) {
            if (inventorySlot[slotIndex].contains(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8)) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    private boolean onMouseReleased(MouseReleasedEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            destinationSlot = setSlotIndex(SlotType.INVENTORY_SLOT);
            if (draggingInventorySlot) equipSlot = setSlotIndex(SlotType.EQUIPMENT_SLOT);
        }
        // Equip Items
        if (equipmentSlot[equipSlot].contains(e.getX() / Game.scale - 16, e.getY() / Game.scale - 16) && inventorySlot[startSlot].hasItem && draggingInventorySlot) {
            equipItem();
        }
        // Unequip Items
        if (inventorySlot[destinationSlot].contains(e.getX() / Game.scale - 8, e.getY() / Game.scale - 8) && !inventorySlot[destinationSlot].hasItem && equipmentSlot[equipSlot].hasItem() && draggingEquipmentSlot) {
            unequipItem();
        }
        // Move Items in Inventory
        if (inventorySlot[destinationSlot].contains(e.getX() / Game.scale - 8, e.getY() / Game.scale - 8) && inventorySlot[startSlot].hasItem && draggingInventorySlot) {
            moveInventoryItem();

        }

        inventorySlot[startSlot].x = xoInv[startSlot];
        inventorySlot[startSlot].y = yoInv[startSlot];
        equipmentSlot[equipSlot].x = xoEquip[equipSlot];
        equipmentSlot[equipSlot].y = yoEquip[equipSlot];

        dragging = false;
        draggingInventorySlot = false;
        draggingEquipmentSlot = false;
        return false;
    }

    private boolean onMouseMoved(MouseMovedEvent e) {
        int x = e.getX() / Game.scale;
        int y = e.getY() / Game.scale;

        if (draggingInventorySlot) {
            inventorySlot[startSlot].x += x - px;
            inventorySlot[startSlot].y += y - py;
            dragging = draggingInventorySlot;
        }
        if (draggingEquipmentSlot) {
            equipmentSlot[equipSlot].x += x - px;
            equipmentSlot[equipSlot].y += y - py;
            dragging = draggingEquipmentSlot;
        }

        px = x;
        py = y;

        return dragging;
    }

    private boolean equipItem() {
        if (equipmentSlot[equipSlot].slotType == inventorySlot[startSlot].itemInSlot.itemType) {
            if (!equipmentSlot[equipSlot].hasItem()) {
                equipmentSlot[equipSlot].addItem(inventorySlot[startSlot].itemInSlot);
                inventorySlot[startSlot].removeItem();
                draggingInventorySlot = false;
                inventorySlot[startSlot].x = xoInv[startSlot];
                inventorySlot[startSlot].y = yoInv[startSlot];
                equipmentSlot[equipSlot].getEquipmentStats();
                player.updateStats();
                Game.sounds[4].play();
                return false;
            }
            if (equipmentSlot[equipSlot].hasItem()) {
                storage = equipmentSlot[equipSlot].itemInSlot;
                equipmentSlot[equipSlot].removeItem();
                equipmentSlot[equipSlot].addItem(inventorySlot[startSlot].itemInSlot);
                inventorySlot[startSlot].removeItem();
                inventorySlot[startSlot].addItem(storage);
                draggingInventorySlot = false;
                inventorySlot[startSlot].x = xoInv[startSlot];
                inventorySlot[startSlot].y = yoInv[startSlot];
                equipmentSlot[equipSlot].getEquipmentStats();
                player.updateStats();
                Game.sounds[4].play();
                return false;
            }
        }
        return false;
    }

    private boolean unequipItem() {
        inventorySlot[destinationSlot].addItem(equipmentSlot[equipSlot].itemInSlot);
        equipmentSlot[equipSlot].removeItem();
        equipmentSlot[equipSlot].x = xoEquip[equipSlot];
        equipmentSlot[equipSlot].y = yoEquip[equipSlot];
        draggingEquipmentSlot = false;
        equipmentSlot[equipSlot].getEquipmentStats();
        player.updateStats();
        return false;
    }

    private boolean moveInventoryItem() {
        if (!inventorySlot[destinationSlot].hasItem) {
            inventorySlot[destinationSlot].addItem(inventorySlot[startSlot].itemInSlot);
            inventorySlot[startSlot].removeItem();
            draggingInventorySlot = false;
            inventorySlot[startSlot].x = xoInv[startSlot];
            inventorySlot[startSlot].y = yoInv[startSlot];
            return false;
        }
        if (inventorySlot[destinationSlot].hasItem) {
            storage = inventorySlot[destinationSlot].itemInSlot;
            inventorySlot[destinationSlot].removeItem();
            inventorySlot[destinationSlot].addItem(inventorySlot[startSlot].itemInSlot);
            inventorySlot[startSlot].removeItem();
            inventorySlot[startSlot].addItem(storage);
            draggingInventorySlot = false;
            inventorySlot[startSlot].x = xoInv[startSlot];
            inventorySlot[startSlot].y = yoInv[startSlot];
            return false;
        }
        return false;
    }

    public void addItemToSlot(Item item) {
        for (int i = 0; i < inventorySlot.length; i++) {
            if (!inventorySlot[i].hasItem) {
                inventorySlot[i].addItem(item);
                inventoryIsFull = false;
                return;
            }
        }
        inventoryIsFull = true;
    }

    public void removeItemFromInventory(ItemType type) {
        for (int i = 0; i < inventorySlot.length; i++) {
            if (inventorySlot[i].hasItem) {
                if (inventorySlot[i].itemInSlot.itemType == type) {
                    inventorySlot[i].removeItem();
                    return;
                }
            }
        }
    }

    public boolean checkInventoryFor(ItemType type) {
        for (int i = 0; i < inventorySlot.length; i++) {
            if (inventorySlot[i].hasItem) {
                if (inventorySlot[i].itemInSlot.itemType == type) {
                    return true;
                }
            }
        }
        return false;
    }

    private int setSlotIndex(SlotType slotType) {
        int slot;
        if (slotType == SlotType.INVENTORY_SLOT) {
            for (slot = 0; slot < inventorySlot.length; slot++) {
                if (inventorySlot[slot].contains(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8)) {
                    if (slot == startSlot) continue;
                    return slot;
                }
            }
        }
        if (slotType == SlotType.EQUIPMENT_SLOT) {
            for (slot = 0; slot < equipmentSlot.length; slot++) {
                if (equipmentSlot[slot].contains(Mouse.getX() / Game.scale - 16, Mouse.getY() / Game.scale - 16)) {
                    return slot;
                }
            }
        }
        return 0;
    }

    public void update() {
        if (!draggingEquipmentSlot || !draggingInventorySlot) {
            for (int i = 0; i < inventorySlot.length; i++) {
                if (inventorySlot[i].contains(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8) && inventorySlot[i].hasItem) {
                    slotIndex = i;
                    showTooltip = true;
                } else {
                    showTooltip = false;
                }
            }
            for (int i = 0; i < equipmentSlot.length; i++) {
                if (equipmentSlot[i].contains(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8) && equipmentSlot[i].hasItem()) {
                    slotIndexEquip = i;
                    showTooltip = true;
                } else {
                    showTooltip = false;
                }
            }
        } else {
            showTooltip = false;
        }
    }

    public void render(Screen screen) {

        screen.renderSprite(290, 65, SpriteSheet.inventory_screen, false);
        for (int i = 0; i < equipmentSlot.length; i++) {
            if (equipmentSlot[i].hasItem() && (i != equipSlot || !draggingEquipmentSlot))
                screen.renderSprite(equipmentSlot[i].x + 1, equipmentSlot[i].y + 1, equipmentSlot[i].itemInSlot.getSprite(), false);
            else if (equipmentSlot[equipSlot].hasItem() && draggingEquipmentSlot)
                screen.renderSprite(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8, equipmentSlot[equipSlot].itemInSlot.icon, false);
        }

        for (int i = 0; i < inventorySlot.length; i++) {
            if (inventorySlot[i].hasItem)
                screen.renderSprite(inventorySlot[i].x, inventorySlot[i].y, inventorySlot[i].itemInSlot.icon, false);
        }
        Inventory.font.render(372, 253, "" + Player.getMoney(), screen);
        // renderTooltip(screen);
        if (inventorySlot[slotIndex].hasItem() && inventorySlot[slotIndex].contains(Mouse.getX() / Game.scale - 8, Mouse.getY() / Game.scale - 8) && !dragging) {
            inventorySlot[slotIndex].renderTooltip(screen);
            if (inventorySlot[slotIndex].hasItem() && slotIndex % 6 > 2)
                Inventory.font.render(Mouse.getX() / Game.scale - 35, Mouse.getY() / Game.scale + 6, "T" + inventorySlot[slotIndex].itemInSlot.tier, screen);
            else if (inventorySlot[slotIndex].hasItem())
                Inventory.font.render(Mouse.getX() / Game.scale + 65, Mouse.getY() / Game.scale + 6, "T" + inventorySlot[slotIndex].itemInSlot.tier, screen);

        }
        if (equipmentSlot[slotIndexEquip].hasItem() && equipmentSlot[slotIndexEquip].contains(Mouse.getX() / Game.scale - 16, Mouse.getY() / Game.scale - 16) && !dragging) {
            equipmentSlot[slotIndexEquip].renderTooltip(screen);
            if (equipmentSlot[slotIndexEquip].hasItem() && (slotIndexEquip == 1 || slotIndexEquip == 5))
                Inventory.font.render(Mouse.getX() / Game.scale - 35, Mouse.getY() / Game.scale + 6, "T" + equipmentSlot[slotIndexEquip].itemInSlot.tier, screen);
            else if (equipmentSlot[slotIndexEquip].hasItem())
                Inventory.font.render(Mouse.getX() / Game.scale + 65, Mouse.getY() / Game.scale + 6, "T" + equipmentSlot[slotIndexEquip].itemInSlot.tier, screen);

        }
    }

}
