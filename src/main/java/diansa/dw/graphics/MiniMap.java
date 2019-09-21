package diansa.dw.graphics;

import diansa.dw.Game;
import diansa.dw.entity.player.Player;
import diansa.dw.level.Level;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

@SuppressWarnings("serial")
public class MiniMap extends Canvas {

    int width = 80;
    int height = 80;
    int scale = 2;

    BufferedImage miniMap = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    int[] pixels = ((DataBufferInt) miniMap.getRaster().getDataBuffer()).getData();

    public void render(Graphics g, Level level, SpriteSheet sheet, Player player) {
        for (int y = 0; y < height; y++) {
            int ya0 = (int) (player.getY() / 16 + y - height / 2);
            for (int x = 0; x < width; x++) {
                int xa0 = (int) (player.getX() / 16 + x - width / 2);
                if (xa0 < 0 || xa0 >= sheet.WIDTH || ya0 < 0 || ya0 >= sheet.HEIGHT) continue;
                int col = sheet.pixels[xa0 + ya0 * sheet.WIDTH];
                if (col == 0xFFFF00FF) col = 0;
                for (int i = 0; i < level.enemies.size(); i++) {
                    if (xa0 == level.enemies.get(i).getX() / 16 && ya0 == level.enemies.get(i).getY() / 16 + 2)
                        col = 0xff0000;
                }
                pixels[x + y * width] = col;
            }
        }

        g.drawImage(miniMap, Game.width * Game.scale - width * scale, 0, width * scale, height * scale, null);
        g.setColor(Color.RED);
        g.fillRect(Game.scale * Game.width - width - 1, height - 1, 3, 3);
    }
}
