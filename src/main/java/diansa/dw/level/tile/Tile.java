package diansa.dw.level.tile;

import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class Tile {

    public int x, y;
    public Sprite sprite;

    // ===== DEFAULT SPRITES/TILES =====
    public static Tile grass = new GrassTile(Sprite.grass);
    public static Tile voidTile = new GrassTile(Sprite.voidSprite);
    public final static int COL_GRASS = 0xFF00FF00;
    // ===== DUNGEON TYPE ONE TILES // LOBBY ==================
    public static Tile portal = new GrassTile(Sprite.portal);
    public static Tile d1_wall_01_top = new SolidTile(Sprite.d1_wall_01_top);
    public static Tile d1_wall_01_bot = new WallTile(Sprite.d1_wall_01_bot);
    public static Tile d1_wall_02_top = new SolidTile(Sprite.d1_wall_02_top);
    public static Tile d1_wall_02_bot = new WallTile(Sprite.d1_wall_02_bot);
    public static Tile d1_wall_03_top = new SolidTile(Sprite.d1_wall_03_top);
    public static Tile d1_wall_03_bot = new WallTile(Sprite.d1_wall_03_bot);
    public static Tile d1_floor_01 = new GrassTile(Sprite.d1_floor_01);
    public static Tile d1_floor_02 = new GrassTile(Sprite.d1_floor_02);
    public static Tile d1_floor_03 = new GrassTile(Sprite.d1_floor_03);
    public static Tile d1_floor_04 = new GrassTile(Sprite.d1_floor_04);
    public static Tile d1_wall_corner_01 = new SolidTile(Sprite.d1_wall_corner_01);
    public static Tile d1_wall_corner_02 = new SolidTile(Sprite.d1_wall_corner_02);
    public static Tile d1_wall_corner_04 = new SolidTile(Sprite.d1_wall_corner_04);
    public static Tile d1_wall_corner_05 = new SolidTile(Sprite.d1_wall_corner_05);
    public static Tile d1_wall_corner_06 = new SolidTile(Sprite.d1_wall_corner_06);
    public static Tile d1_wall_corner_07 = new SolidTile(Sprite.d1_wall_corner_07);
    public static Tile d1_wall_corner_08 = new SolidTile(Sprite.d1_wall_corner_08);
    public static Tile d1_wall_corner_10 = new SolidTile(Sprite.d1_wall_corner_10);
    public static Tile d1_wall_corner_11 = new SolidTile(Sprite.d1_wall_corner_11);
    public static Tile d1_wall_corner_13 = new SolidTile(Sprite.d1_wall_corner_13);
    public static Tile d1_wall_corner_14 = new SolidTile(Sprite.d1_wall_corner_14);
    public static Tile d1_wall_corner_16 = new SolidTile(Sprite.d1_wall_corner_16);

    public static Tile d1_rug_corner_01 = new GrassTile(Sprite.d1_rug_corner_01);
    public static Tile d1_rug_corner_02 = new GrassTile(Sprite.d1_rug_corner_02);
    public static Tile d1_rug_corner_03 = new GrassTile(Sprite.d1_rug_corner_03);
    public static Tile d1_rug_corner_04 = new GrassTile(Sprite.d1_rug_corner_04);
    public static Tile d1_rug_corner_05 = new GrassTile(Sprite.d1_rug_corner_05);
    public static Tile d1_rug_corner_06 = new GrassTile(Sprite.d1_rug_corner_06);
    public static Tile d1_rug_corner_07 = new GrassTile(Sprite.d1_rug_corner_07);
    public static Tile d1_rug_corner_08 = new GrassTile(Sprite.d1_rug_corner_08);
    public static Tile d1_rug_corner_09 = new GrassTile(Sprite.d1_rug_corner_09);
    // ===== DUNGEON TYPE ONE COLORS ===========================
    public final static int col_portal = 0xFFFFFAC4;
    public final static int col_d1_wall_01_top = 0xFF770000;
    public final static int col_d1_wall_01_bot = 0xFF;
    public final static int col_d1_wall_02_top = 0xFF6C1E70;
    public final static int col_d1_wall_02_bot = 0xFF8F2793;
    public final static int col_d1_wall_03_top = 0xFF383838;
    public final static int col_d1_wall_03_bot = 0xFF636363;
    public final static int col_d1_floor_01 = 0xFF68696B;
    public final static int col_d1_floor_02 = 0xFFA0A0A0;
    public final static int col_d1_floor_03 = 0xFF4848C4;
    public final static int col_d1_floor_04 = 0xFF5197C6;
    public final static int col_d1_wall_corner_01 = 0xFF770000;
    public final static int col_d1_wall_corner_02 = 0xFF0000B5;
    public final static int col_d1_wall_corner_04 = 0xFF156808;
    public final static int col_d1_wall_corner_05 = 0xFF683378;
    public final static int col_d1_wall_corner_06 = 0xFF00D0FF;
    public final static int col_d1_wall_corner_07 = 0xFF008FAF;
    public final static int col_d1_wall_corner_08 = 0xFFD8C304;
    public final static int col_d1_wall_corner_10 = 0xFF006E87;
    public final static int col_d1_wall_corner_11 = 0xFF183C44;
    public final static int col_d1_wall_corner_13 = 0xFFFF000C;
    public final static int col_d1_wall_corner_14 = 0xFF2C8C99;
    public final static int col_d1_wall_corner_16 = 0xFF28B70E;

    public final static int col_d1_rug_corner_01 = 0xFF420004;
    public final static int col_d1_rug_corner_02 = 0xFF880004;
    public final static int col_d1_rug_corner_03 = 0xFFBA0004;
    public final static int col_d1_rug_corner_04 = 0xFF380002;
    public final static int col_d1_rug_corner_05 = 0xFF5B0004;
    public final static int col_d1_rug_corner_06 = 0xFF8E0007;
    public final static int col_d1_rug_corner_07 = 0xFF5B0055;
    public final static int col_d1_rug_corner_08 = 0xFF5B007C;
    public final static int col_d1_rug_corner_09 = 0xFF5B00B2;
    // =================

    // ================= TEST AREA ====================================
    public final static int col_tt_01 = 0xFF4C1C03;

    // ================================================================
    public static Tile test_tile_01 = new GrassTile(Sprite.tt_01);

    // =================================================================

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Screen screen) {
    }

    public boolean solid() {
        return false;
    }
}
