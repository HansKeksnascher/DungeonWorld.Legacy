package diansa.dw.entity.item;

import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class KeyItem extends Item {

    public boolean keyUsed = false;

    public enum KeyType {
        TREASURE_ROOM, TREASURE_CHEST
    }

    public KeyType keyType;

    public KeyItem() {
        keyType = KeyType.TREASURE_CHEST;
        itemType = ItemType.KEY;
        dropSprite = Sprite.treasureChestKey;
        icon = Sprite.treasureChestKey_icon;

        statName[0] = "TREASURE";
        statName[1] = "CHEST";
        statName[2] = "KEY";
        statValue[0] = 1;
        statValue[1] = 1;
        statValue[2] = 1;
    }

    public KeyItem(int x, int y, KeyType keyType) {
        super(x, y);
        this.keyType = keyType;
        itemType = ItemType.KEY;
        dropSprite = Sprite.treasureChestKey;
        icon = Sprite.treasureChestKey_icon;

        statName[0] = "TREASURE";
        statName[1] = "CHEST";
        statName[2] = "KEY";
        statValue[0] = 1;
        statValue[1] = 1;
        statValue[2] = 1;
    }

    public void update() {
        super.update();
        if (keyUsed) {
            remove();
            return;
        }
    }

    public void render(Screen screen) {
        screen.renderSprite(x - 16, y - 16, dropSprite, true);
    }
}
