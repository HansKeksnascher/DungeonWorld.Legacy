package diansa.dw.entity.npc;

import diansa.dw.Game;
import diansa.dw.graphics.AnimatedSprite;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.graphics.UI.UIActionListener;
import diansa.dw.graphics.UI.UIButton;
import diansa.dw.graphics.UI.UIPanel;
import diansa.dw.graphics.UIManager;
import diansa.dw.util.Vector2i;

public class GeneralShopNPC extends NPC {

    private AnimatedSprite animSprite = new AnimatedSprite(SpriteSheet.shop_npc_idle, 32, 32, 1);

    public static boolean shopIsOpen = false;

    protected UIManager ui;

    public GeneralShopNPC(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = animSprite;
    }

    public void update() {
        if (player == null && level.getClientPlayer() != null) {
            System.out.println("Npc sees player");
            player = level.getClientPlayer();
        }

        if (x >= (player.getX() - 16) && x < (player.getX() + 16) && y >= (player.getY() - 32) && y <= (player.getY() + 8)) {
            if (Game.keyInput.action && !actionPerformed) {

                if (!shopIsOpen) {
                    openShop();

                    shopIsOpen = true;
                }
                actionPerformed = true;
            }
        }
    }

    private void openShop() {
        System.out.println("Hallo! Was m�chten Sie kaufen?");
        ui = Game.getUIManager();

        UIPanel panel = (UIPanel) new UIPanel(new Vector2i(50, 50), new Vector2i(200, 300));
        ui.addPanel(panel);

        UIButton close = new UIButton(new Vector2i(115, 250), new Vector2i(75, 30), new UIActionListener() {
            public void perform() {
                ui.removePanel(panel);
                shopIsOpen = false;
            }
        });
        close.setText("CLOSE");
        panel.addComponent(close);
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
    }
}
