package diansa.dw.entity.npc;

import diansa.dw.Game;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class Teleport extends NPC {

    private int destX, destY;

    public Teleport(int x, int y, int destX, int destY) {
        this.x = x << 4;
        this.y = y << 4;
        this.destX = destX << 4;
        this.destY = destY << 4;
        sprite = Sprite.portal;
    }

    public void update() {
        // TODO: player == null ????? WTF hier stimmt was nicht :(
        if (player == null && level.getClientPlayer() != null) {
            player = level.getClientPlayer();
        }

        if (x >= (player.getX() - 16) && x < (player.getX() + 16) &&
                y >= (player.getY() - 8) && y <= (player.getY() + 8)) {
            if (Game.keyInput.action && !actionPerformed) {
                System.out.println("Porting player to destination....");
                player.setX(destX);
                player.setY(destY);
                actionPerformed = true;
                System.out.println("Anzahl Spieler: " + level.players.size());
            }
        }
    }

    public void render(Screen screen) {
        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
    }
}
