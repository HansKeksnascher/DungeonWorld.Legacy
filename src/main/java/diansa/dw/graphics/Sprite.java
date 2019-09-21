package diansa.dw.graphics;

public class Sprite {

    public final int SIZE;
    public int x;
    public int y;
    private int width, height;
    public int[] pixels;
    protected SpriteSheet sheet;

    // ===== DEFAULT SPRITES/TILES =====
    public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.defaultSprite);
    public static Sprite voidSprite = new Sprite(16, 0);
    // ===== PROJECTILES ================================
    public static Sprite double_bullet = new Sprite(16, 0, 2, SpriteSheet.projectiles);
    // ===== EQUIPMENT & ITEMS ==========================
    public static Sprite pistol = new Sprite(32, 0, 0, SpriteSheet.equipment);
    public static Sprite pistol_normal = new Sprite(32, 0, 3, SpriteSheet.equipment);
    public static Sprite pistol_magic = new Sprite(32, 0, 4, SpriteSheet.equipment);
    public static Sprite pistol_rare = new Sprite(32, 0, 2, SpriteSheet.equipment);
    public static Sprite pistol_unique = new Sprite(32, 0, 5, SpriteSheet.equipment);
    public static Sprite pistol_icon_normal = new Sprite(16, 0, 0, SpriteSheet.item_icon);
    public static Sprite pistol_icon_magic = new Sprite(16, 0, 1, SpriteSheet.item_icon);
    public static Sprite pistol_icon_rare = new Sprite(16, 0, 2, SpriteSheet.item_icon);
    public static Sprite pistol_icon_unique = new Sprite(16, 0, 3, SpriteSheet.item_icon);


    public static Sprite armor = new Sprite(32, 2, 0, SpriteSheet.equipment);
    public static Sprite armor_normal = new Sprite(32, 2, 3, SpriteSheet.equipment);
    public static Sprite armor_magic = new Sprite(32, 2, 4, SpriteSheet.equipment);
    public static Sprite armor_rare = new Sprite(32, 2, 2, SpriteSheet.equipment);
    public static Sprite armor_unique = new Sprite(32, 2, 5, SpriteSheet.equipment);
    public static Sprite armor_icon_normal = new Sprite(16, 1, 0, SpriteSheet.item_icon);
    public static Sprite armor_icon_magic = new Sprite(16, 1, 1, SpriteSheet.item_icon);
    public static Sprite armor_icon_rare = new Sprite(16, 1, 2, SpriteSheet.item_icon);
    public static Sprite armor_icon_unique = new Sprite(16, 1, 3, SpriteSheet.item_icon);

    public static Sprite ring = new Sprite(32, 3, 0, SpriteSheet.equipment);
    public static Sprite ring_normal = new Sprite(32, 3, 3, SpriteSheet.equipment);
    public static Sprite ring_magic = new Sprite(32, 3, 4, SpriteSheet.equipment);
    public static Sprite ring_rare = new Sprite(32, 3, 2, SpriteSheet.equipment);
    public static Sprite ring_unique = new Sprite(32, 3, 5, SpriteSheet.equipment);
    public static Sprite ring_icon_normal = new Sprite(16, 2, 0, SpriteSheet.item_icon);
    public static Sprite ring_icon_magic = new Sprite(16, 2, 1, SpriteSheet.item_icon);
    public static Sprite ring_icon_rare = new Sprite(16, 2, 2, SpriteSheet.item_icon);
    public static Sprite ring_icon_unique = new Sprite(16, 2, 3, SpriteSheet.item_icon);

    public static Sprite gloves = new Sprite(32, 4, 0, SpriteSheet.equipment);
    public static Sprite gloves_normal = new Sprite(32, 4, 3, SpriteSheet.equipment);
    public static Sprite gloves_magic = new Sprite(32, 4, 4, SpriteSheet.equipment);
    public static Sprite gloves_rare = new Sprite(32, 4, 2, SpriteSheet.equipment);
    public static Sprite gloves_unique = new Sprite(32, 4, 5, SpriteSheet.equipment);
    public static Sprite gloves_icon_normal = new Sprite(16, 3, 0, SpriteSheet.item_icon);
    public static Sprite gloves_icon_magic = new Sprite(16, 3, 1, SpriteSheet.item_icon);
    public static Sprite gloves_icon_rare = new Sprite(16, 3, 2, SpriteSheet.item_icon);
    public static Sprite gloves_icon_unique = new Sprite(16, 3, 3, SpriteSheet.item_icon);

    public static Sprite boots = new Sprite(32, 5, 0, SpriteSheet.equipment);
    public static Sprite boots_normal = new Sprite(32, 5, 3, SpriteSheet.equipment);
    public static Sprite boots_magic = new Sprite(32, 5, 4, SpriteSheet.equipment);
    public static Sprite boots_rare = new Sprite(32, 5, 2, SpriteSheet.equipment);
    public static Sprite boots_unique = new Sprite(32, 5, 5, SpriteSheet.equipment);
    public static Sprite boots_icon_normal = new Sprite(16, 4, 0, SpriteSheet.item_icon);
    public static Sprite boots_icon_magic = new Sprite(16, 4, 1, SpriteSheet.item_icon);
    public static Sprite boots_icon_rare = new Sprite(16, 4, 2, SpriteSheet.item_icon);
    public static Sprite boots_icon_unique = new Sprite(16, 4, 3, SpriteSheet.item_icon);

    public static Sprite neck = new Sprite(32, 1, 0, SpriteSheet.equipment);
    public static Sprite neck_normal = new Sprite(32, 1, 3, SpriteSheet.equipment);
    public static Sprite neck_magic = new Sprite(32, 1, 4, SpriteSheet.equipment);
    public static Sprite neck_rare = new Sprite(32, 1, 2, SpriteSheet.equipment);
    public static Sprite neck_unique = new Sprite(32, 1, 5, SpriteSheet.equipment);
    public static Sprite neck_icon_normal = new Sprite(16, 5, 0, SpriteSheet.item_icon);
    public static Sprite neck_icon_magic = new Sprite(16, 5, 1, SpriteSheet.item_icon);
    public static Sprite neck_icon_rare = new Sprite(16, 5, 2, SpriteSheet.item_icon);
    public static Sprite neck_icon_unique = new Sprite(16, 5, 3, SpriteSheet.item_icon);

    // ===== PARTICLE ===================================
    public static Sprite red_particle = new Sprite(2, 0xFF0000);
    public static Sprite legendary_drop = new Sprite(2, 0xAF2BDB);
    public static Sprite normal_drop = new Sprite(2, 0x2DD100);
    public static Sprite rare_drop = new Sprite(2, 0x0B07EF);
    public static Sprite epic_drop = new Sprite(2, 0xF70400);
    // ===== STUFF =====================================
    public static Sprite treasureChest = new Sprite(32, 0, 0, SpriteSheet.decorations);
    public static Sprite treasureChestKey = new Sprite(32, 1, 0, SpriteSheet.decorations);
    public static Sprite treasureChestKey_icon = new Sprite(16, 6, 0, SpriteSheet.item_icon);
    // ===== DUNGEON TYPE ONE ===========================
    public static Sprite portal = new Sprite(32, 4, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_01_top = new Sprite(16, 0, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_01_bot = new Sprite(16, 0, 1, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_02_top = new Sprite(16, 1, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_02_bot = new Sprite(16, 1, 1, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_03_top = new Sprite(16, 2, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_03_bot = new Sprite(16, 2, 1, SpriteSheet.dungeon_type_1);
    public static Sprite d1_floor_01 = new Sprite(16, 0, 2, SpriteSheet.dungeon_type_1);
    public static Sprite d1_floor_02 = new Sprite(16, 1, 2, SpriteSheet.dungeon_type_1);
    public static Sprite d1_floor_03 = new Sprite(16, 2, 2, SpriteSheet.dungeon_type_1);
    public static Sprite d1_floor_04 = new Sprite(16, 3, 2, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_01 = new Sprite(16, 0, 3, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_02 = new Sprite(16, 1, 3, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_04 = new Sprite(16, 3, 3, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_05 = new Sprite(16, 0, 4, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_06 = new Sprite(16, 1, 4, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_07 = new Sprite(16, 2, 4, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_08 = new Sprite(16, 3, 4, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_10 = new Sprite(16, 1, 5, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_11 = new Sprite(16, 2, 5, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_13 = new Sprite(16, 0, 6, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_14 = new Sprite(16, 1, 6, SpriteSheet.dungeon_type_1);
    public static Sprite d1_wall_corner_16 = new Sprite(16, 3, 6, SpriteSheet.dungeon_type_1);


    public static Sprite d1_rug_corner_01 = new Sprite(16, 4, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_02 = new Sprite(16, 5, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_03 = new Sprite(16, 6, 0, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_04 = new Sprite(16, 4, 1, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_05 = new Sprite(16, 5, 1, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_06 = new Sprite(16, 6, 1, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_07 = new Sprite(16, 4, 2, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_08 = new Sprite(16, 5, 2, SpriteSheet.dungeon_type_1);
    public static Sprite d1_rug_corner_09 = new Sprite(16, 6, 2, SpriteSheet.dungeon_type_1);

    // ===== DUNGEON TYPE TWO ===========================

    // ============ TEST AREA ===============================
    public static Sprite tt_01 = new Sprite(16, 4, 3, SpriteSheet.dungeon_type_1);

    public Sprite(int size, int color) {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }

    public Sprite(SpriteSheet sheet, int width, int height) {
        SIZE = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
    }

    public Sprite(int[] pixels, int width, int height) {
        SIZE = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            this.pixels[i] = pixels[i];
        }
    }

    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.WIDTH * sheet.HEIGHT);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.WIDTH * sheet.HEIGHT];
        for (int yp = 0; yp < sheet.getHeight() / sheet.HEIGHT; yp++) {
            for (int xp = 0; xp < sheet.getWidth() / sheet.WIDTH; xp++) {

                for (int y = 0; y < sheet.HEIGHT; y++) {
                    for (int x = 0; x < sheet.WIDTH; x++) {
                        int xo = x + xp * sheet.WIDTH;
                        int yo = y + yp * sheet.HEIGHT;
                        pixels[x + y * sheet.WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.WIDTH, sheet.HEIGHT);
            }
        }
        return sprites;
    }

    public static Sprite rotate(Sprite sprite, double angle) {
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];

        double nx_x = rotX(-angle, 1.0, 0.0);
        double nx_y = rotY(-angle, 1.0, 0.0);
        double ny_x = rotX(-angle, 0.0, 1.0);
        double ny_y = rotY(-angle, 0.0, 1.0);

        double x0 = rotX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rotY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col = 0;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    private static double rotX(double angle, double x, double y) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return x * cos + y * -sin;
    }

    private static double rotY(double angle, double x, double y) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return x * sin + y * cos;
    }

    private void setColor(int color) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = color;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
        }
    }
}
