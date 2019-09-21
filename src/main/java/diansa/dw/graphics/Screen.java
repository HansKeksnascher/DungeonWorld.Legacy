package diansa.dw.graphics;

import diansa.dw.entity.projectile.Projectile;
import diansa.dw.level.Level;
import diansa.dw.level.tile.Tile;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Screen {

    public final BufferedImage image;
    public int width, height;
    public static final int MAP_SIZE = 64;
    public static final int MAP_SIZE_MASK = MAP_SIZE - 1;
    public int[] pixels;
    public Level level;
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    public int xOffset, yOffset;

    private final int ALPHA_COL = 0xffff00ff;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void init(Level level) {
        this.level = level;
    }

    public void clearScreen() {
        Arrays.fill(pixels, 0);
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    // ===== render methods here =====
    public void renderTile(int xp, int yp, Tile tile) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < tile.sprite.SIZE; y++) {
            int ya = y + yp;
            for (int x = 0; x < tile.sprite.SIZE; x++) {
                int xa = x + xp;
                if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                if (tile.sprite.pixels[x + y * tile.sprite.SIZE] == ALPHA_COL) continue;
                pixels[xa + ya * width] = 0xFF000000 | tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }

    public void renderMob(int xp, int yp, Sprite sprite, double size) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < size; y++) {
            int ya = y + yp;
            for (int x = 0; x < size; x++) {
                int xa = x + xp;
                if (xa < -(size - 1) || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = sprite.pixels[(int) (x + y * size)];
                if (col != 0xffff00ff) pixels[xa + ya * width] = 0xFF000000 | col;
            }
        }
    }

    public void renderMob(int xp, int yp, Sprite sprite, int flip) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < 32; y++) {
            int ya = y + yp;
            int ys = y;
            if (flip == 2 || flip == 3) {
                ys = 31 - y;
            }
            for (int x = 0; x < 32; x++) {
                int xa = x + xp;
                int xs = x;
                if (flip == 1 || flip == 3) {
                    xs = 31 - x;
                }
                if (xa < -31 || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = sprite.pixels[xs + ys * 32];
                if (col != 0xffff00ff) pixels[xa + ya * width] = 0xFF000000 | col;
            }
        }
    }

    public void renderProjectile(int xp, int yp, Projectile p) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < p.getSpriteSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < p.getSpriteSize(); x++) {
                int xa = x + xp;
                if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
                if (col != ALPHA_COL) pixels[xa + ya * width] = 0xFF000000 | col;
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
        if (fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int xa = x + xp;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                int col = sprite.pixels[x + y * sprite.getWidth()];
                if (col == 0xFFFF00FF) continue;
                pixels[xa + ya * width] = 0xFF000000 | col;
            }
        }
    }

    public void renderSprite(int xp, int yp, SpriteSheet sheet, boolean fixed) {
        if (fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sheet.HEIGHT; y++) {
            int ya = y + yp;
            for (int x = 0; x < sheet.WIDTH; x++) {
                int xa = x + xp;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                int col = sheet.pixels[x + y * sheet.WIDTH];
                if (col != 0xFFFF00FF) pixels[xa + ya * width] = 0xFF000000 | col;
            }
        }
    }

    public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
        if (fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int x = xp; x < xp + width; x++) {
            if (x < 0 | x >= this.width || yp >= this.height) continue;
            if (yp > 0) pixels[x + yp * this.width] = 0xFF000000 | color;
            if (yp + height >= this.height) continue;
            if (yp + height > 0) pixels[x + (yp + height) * this.width] = 0xFF000000 | color;
        }
        for (int y = yp; y <= yp + height; y++) {
            if (xp >= this.width || y < 0 || y >= this.height) continue;
            if (xp > 0) pixels[xp + y * this.width] = 0xFF000000 | color;
            if (xp + width >= this.width) continue;
            if (xp + width > 0) pixels[(xp + width) + y * this.width] = 0xFF000000 | color;
        }
    }

    public void fillRect(int xp, int yp, int width, int height, int color, boolean fixed) {
        if (fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < height; y++) {
            int yo = yp + y;
            if (yo < 0 || yo >= this.height) continue;
            for (int x = 0; x < width; x++) {
                int xo = xp + x;
                if (xo < 0 || xo >= this.width) continue;
                pixels[xo + yo * this.width] = 0xFF000000 | color;
            }
        }
    }

    public void renderHPBar(int x, int y, int width, int height, int health, boolean fixed) {
        drawRect(x, y, width, height, 0xDBDBDB, fixed);
        fillRect(x + 1, y + 1, width - 1, height - 1, 0xFF0000, fixed);
        fillRect(x + 1, y + 1, health - 1, height - 1, 0x00B700, fixed);
    }

    // public void renderMiniMap(SpriteSheet sheet, Player player) {
    // for (int y = 0; y < 80; y++) {
    // int ya0 = player.getY() / 16 + y - 40;
    // for (int x = 0; x < 80; x++) {
    // int xa0 = player.getX() / 16 + x - 40;
    // if (xa0 < 0 || xa0 >= sheet.WIDTH || ya0 < 0 || ya0 >= sheet.HEIGHT) continue;
    // int col = sheet.pixels[xa0 + ya0 * sheet.WIDTH];
    // if(col == 0xffFF00FF)continue;
    // for(int i = 0; i < level.enemies.size(); i++){
    //
    // if(xa0 == level.enemies.get(i).getX()/16 && ya0 == level.enemies.get(i).getY()/16 + 2) col = 0xff0000;
    // }
    // pixels[350 + x + (y + 10) * width] = col;
    // }
    // }
    // fillRect(390, 50, 3, 3, 0xFF00FF, false);
    // }

    // ===============================

}
