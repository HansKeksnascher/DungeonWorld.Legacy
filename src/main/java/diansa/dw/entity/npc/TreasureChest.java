package diansa.dw.entity.npc;

import diansa.dw.Game;
import diansa.dw.entity.item.Item.ItemType;
import diansa.dw.entity.player.Player;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class TreasureChest extends NPC {

    private boolean needKey = false;

    public TreasureChest(int x, int y, boolean needKey) {
        this.x = x << 4;
        this.y = y << 4;
        this.needKey = needKey;
        sprite = Sprite.treasureChest;
    }

    public void update() {
        super.update();

        if (x >= (player.getX() - 16) && x < (player.getX() + 16) && y >= (player.getY() - 40) && y <= (player.getY())) {

            if (needKey) {
                if (Game.keyInput.action && !actionPerformed && Player.getPlayerInventory().checkInventoryFor(ItemType.KEY)) {
                    dropChestLoot();
                    actionPerformed = true;
                    Player.getPlayerInventory().removeItemFromInventory(ItemType.KEY);
                    remove();
                    return;
                } else if (Game.keyInput.action && !actionPerformed) {
                    System.out.println("You don't have a key!");
                    actionPerformed = true;
                }
            } else {
                if (Game.keyInput.action && !actionPerformed) {
                    dropChestLoot();
                    actionPerformed = true;
                    remove();
                    return;
                }
            }
        }

    }

    public void render(Screen screen) {
        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32.0);
    }
}
