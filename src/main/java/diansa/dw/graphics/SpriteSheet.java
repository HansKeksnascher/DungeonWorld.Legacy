package diansa.dw.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    public final int SIZE;
    public final int WIDTH, HEIGHT;
    public int[] pixels;
    private String path;
    private Sprite[] sprites;
    private int width, height;

    public static SpriteSheet defaultSprite = new SpriteSheet("/texture/world/default.png", 160);
    public static SpriteSheet inventory_screen = new SpriteSheet("/texture/inventory_screen.png", 148, 200);
    public static SpriteSheet character_screen = new SpriteSheet("/texture/character_screen.png", 150, 200);
    public static SpriteSheet tooltip = new SpriteSheet("/texture/tooltip.png", 100, 50);
    public static SpriteSheet tooltipV2 = new SpriteSheet("/texture/tooltipV2.png", 100, 70);

    public static SpriteSheet drop_frame_legend = new SpriteSheet("/texture/frame_test_legend.png", 48, 16);
    public static SpriteSheet drop_frame_legend1 = new SpriteSheet("/texture/frame_test_legend1.png", 48, 16);
    public static SpriteSheet drop_frame_legend2 = new SpriteSheet("/texture/frame_test_legend2.png", 48, 16);
    public static SpriteSheet drop_frame_normal = new SpriteSheet("/texture/frame_test_normal.png", 48, 16);
    public static SpriteSheet drop_frame_rare = new SpriteSheet("/texture/frame_test_rare.png", 48, 16);
    public static SpriteSheet drop_frame_epic = new SpriteSheet("/texture/frame_test.png", 48, 16);

    public static SpriteSheet npc_sprites_01 = new SpriteSheet("/texture/entity/npc_sprites_01.png", 256, 128);
    public static SpriteSheet equipment = new SpriteSheet("/texture/equipment.png", 192);
    public static SpriteSheet item_icon = new SpriteSheet("/texture/item_icons.png", 160);
    // ===== BIOME SPRITES ===========================
    public static SpriteSheet dungeon_type_1 = new SpriteSheet("/texture/world/dungeon_type_1.png", 160);
    // ===== PROJECTILES ============================
    public static SpriteSheet projectiles = new SpriteSheet("/texture/projectiles.png", 64);
    // ===== PLAYER SPRITES =========================
    // ===== GUNSLINGER =============================
    public static SpriteSheet gunslinger = new SpriteSheet("/texture/player/gunslinger.png", 256, 128);
    public static SpriteSheet gunslinger_new = new SpriteSheet("/texture/player/gunslinger_new.png", 256, 128);
    public static SpriteSheet gs_stay_side = new SpriteSheet(gunslinger_new, 0, 0, 3, 1, 32);
    public static SpriteSheet gs_stay_up = new SpriteSheet(gunslinger_new, 0, 1, 3, 1, 32);
    public static SpriteSheet gs_stay_down = new SpriteSheet(gunslinger_new, 0, 2, 3, 1, 32);
    public static SpriteSheet gs_stay_upside = new SpriteSheet(gunslinger_new, 0, 3, 3, 1, 32);

    public static SpriteSheet gs_move_side = new SpriteSheet(gunslinger_new, 3, 0, 4, 1, 32);
    public static SpriteSheet gs_move_up = new SpriteSheet(gunslinger_new, 3, 1, 3, 1, 32);
    public static SpriteSheet gs_move_down = new SpriteSheet(gunslinger_new, 3, 2, 3, 1, 32);
    public static SpriteSheet gs_move_upside = new SpriteSheet(gunslinger_new, 3, 3, 3, 1, 32);
    // ===== ARCHER =================================

    // ===== FLAMETHROWER ===========================
    public static SpriteSheet flamethrower = new SpriteSheet("/texture/player/flamethrower.png", 256, 128);
    public static SpriteSheet ft_stay_side = new SpriteSheet(flamethrower, 0, 0, 3, 1, 32);
    public static SpriteSheet ft_stay_up = new SpriteSheet(flamethrower, 0, 1, 3, 1, 32);
    public static SpriteSheet ft_stay_down = new SpriteSheet(flamethrower, 0, 2, 3, 1, 32);
    public static SpriteSheet ft_stay_upside = new SpriteSheet(flamethrower, 0, 3, 3, 1, 32);

    public static SpriteSheet ft_move_side = new SpriteSheet(flamethrower, 3, 0, 3, 1, 32);
    public static SpriteSheet ft_move_up = new SpriteSheet(flamethrower, 3, 1, 3, 1, 32);
    public static SpriteSheet ft_move_down = new SpriteSheet(flamethrower, 3, 2, 3, 1, 32);
    public static SpriteSheet ft_move_upside = new SpriteSheet(flamethrower, 3, 3, 3, 1, 32);


    // ===== MOB SPRITES ============================
    public static SpriteSheet archer_mob = new SpriteSheet("/texture/entity/archer_mob.png", 256, 128);
    public static SpriteSheet archer_mob_stay_side = new SpriteSheet(archer_mob, 0, 0, 3, 1, 32);
    public static SpriteSheet archer_mob_stay_up = new SpriteSheet(archer_mob, 0, 1, 3, 1, 32);
    public static SpriteSheet archer_mob_stay_down = new SpriteSheet(archer_mob, 0, 2, 3, 1, 32);
    public static SpriteSheet archer_mob_stay_upside = new SpriteSheet(archer_mob, 0, 3, 3, 1, 32);

    public static SpriteSheet archer_mob_move_side = new SpriteSheet(archer_mob, 3, 0, 3, 1, 32);
    public static SpriteSheet archer_mob_move_up = new SpriteSheet(archer_mob, 3, 1, 3, 1, 32);
    public static SpriteSheet archer_mob_move_down = new SpriteSheet(archer_mob, 3, 2, 3, 1, 32);
    public static SpriteSheet archer_mob_move_upside = new SpriteSheet(archer_mob, 3, 3, 3, 1, 32);

    // ===== NPC SPRITES ==============================
    public static SpriteSheet shop_npc_idle = new SpriteSheet(npc_sprites_01, 0, 0, 1, 1, 32);
    public static SpriteSheet decorations = new SpriteSheet("/texture/entity/decorations.png", 320);

    // ===== MAPS =====================================
    public static SpriteSheet dungeon_1_1_map = new SpriteSheet("/level/dungeon_1_1_map.png", 200, 180);

    public SpriteSheet(String path, int size) {
        this.path = path;
        SIZE = size;
        WIDTH = size;
        HEIGHT = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        SIZE = -1;
        WIDTH = width;
        HEIGHT = height;
        pixels = new int[WIDTH * HEIGHT];
        load();
    }

    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        if (width == height) SIZE = width;
        else
            SIZE = -1;
        WIDTH = w;
        HEIGHT = h;
        pixels = new int[w * h];
        for (int y0 = 0; y0 < h; y0++) {
            int yp = yy + y0;
            for (int x0 = 0; x0 < w; x0++) {
                int xp = xx + x0;
                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
            }
        }
        int frame = 0;
        sprites = new Sprite[width * height];
        for (int ya = 0; ya < height; ya++) {
            for (int xa = 0; xa < width; xa++) {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for (int y0 = 0; y0 < spriteSize; y0++) {
                    for (int x0 = 0; x0 < spriteSize; x0++) {
                        spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
