package diansa.dw.entity.npc;

import diansa.dw.Game;
import diansa.dw.entity.Entity;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class Portal extends NPC {

    private int world;

    public Portal(int x, int y, int world) {
        this.x = x << 4;
        this.y = y << 4;
        // TODO world implementation
        sprite = Sprite.portal;
        this.world = world;
    }

    public void update() {

        if (player == null && level.getClientPlayer() != null) {
            player = level.getClientPlayer();
        }

        // TODO:
        if (x >= (player.getX() - 16) && x < (player.getX() + 16) && y >= (player.getY() - 16) && y <= (player.getY() + 16)) {
            if (Game.keyInput.action && !actionPerformed) {
                Entity.world = world;
                System.out.println(world);
                actionPerformed = true;
                shouldTeleport = true;
                System.out.println("Anzahl Spieler: " + level.players.size());
            }
        }
    }

    public void render(Screen screen) {
        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
    }

}
