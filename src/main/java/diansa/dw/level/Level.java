package diansa.dw.level;

import diansa.dw.entity.Entity;
import diansa.dw.entity.item.Item;
import diansa.dw.entity.mob.Mob;
import diansa.dw.entity.npc.NPC;
import diansa.dw.entity.particle.Particle;
import diansa.dw.entity.player.Player;
import diansa.dw.entity.projectile.Projectile;
import diansa.dw.events.Event;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.layers.Layer;
import diansa.dw.level.tile.Tile;
import diansa.dw.level.tile.WallTile;
import diansa.dw.util.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Level extends Layer {
    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;
    public static int spawnX = 0;
    public static int spawnY = 0;
    protected static Random random = new Random();
    private int xScroll, yScroll;

    public static int worldLevel = 1;

    public List<Mob> players = new ArrayList<Mob>();
    public List<NPC> npcs = new ArrayList<NPC>();
    public List<Mob> enemies = new ArrayList<Mob>();
    public List<Projectile> projectiles = new ArrayList<Projectile>();
    public List<Particle> particles = new ArrayList<Particle>();
    public List<Item> items = new ArrayList<Item>();

    public static Level currentLevel = null;

    private Comparator<Node> nodeSorter = new Comparator<Node>() {
        public int compare(Node n0, Node n1) {
            if (n1.fCost < n0.fCost) return +1;
            if (n1.fCost > n0.fCost) return -1;
            return 0;
        }
    };

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }

    public Level(String path) {
        loadLevel(path);
        generateLevel();
    }

    public static Level setLevel(int index) {
        if (index > 10) index = 0;
        switch (index) {
            case 0:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new LobbyLevel("/level/world_lobby.png");
            case 1:
                spawnX = 100 << 4;
                spawnY = 125 << 4;
                if (currentLevel == null) {
                    worldLevel = Player.getPlayerLevel();
                    currentLevel = new Dungeon_1_1("/level/dungeon_1_1.png");
                    return currentLevel;
                } else {
                    return currentLevel;
                }

            case 2:
                spawnX = 175 << 4;
                spawnY = 100 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_1_2("/level/dungeon_1_2.png");
            case 3:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_1_3("/level/dungeon_1_3.png");
            case 4:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_2_1("/level/dungeon_2_1.png");
            case 5:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_2_2("/level/dungeon_2_2.png");
            case 6:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_2_3("/level/dungeon_2_3.png");
            case 7:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_3_1("/level/dungeon_3_1.png");
            case 8:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_3_2("/level/dungeon_3_2.png");
            case 9:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_3_3("/level/dungeon_3_3.png");
            case 10:
                spawnX = 40 << 4;
                spawnY = 20 << 4;
                worldLevel = Player.getPlayerLevel();
                return new Dungeon_4("/level/dungeon_4.png");
            default:
                worldLevel = 1;
                return new DefaultLevel("/level/spawn.png");

        }

    }

    public void setScroll(int xScroll, int yScroll) {
        this.xScroll = xScroll;
        this.yScroll = yScroll;
    }

    protected void generateLevel() {

    }

    protected void loadLevel(String path) {

    }

    public void onEvent(Event event) {
        Player player = getClientPlayer();
        if (player != null) {
            player.onEvent(event);
        }
    }

    public Player getClientPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        return (Player) players.get(0);
    }

    public Player getPlayerAt(int index) {
        return (Player) players.get(index);
    }

    public List<Mob> getPlayers(Entity e, int radius) {
        List<Mob> result = new ArrayList<Mob>();
        int ex = (int) e.getX();
        int ey = (int) e.getY();
        for (int i = 0; i < players.size(); i++) {
            Player player = (Player) players.get(i);
            int x = (int) player.getX();
            int y = (int) player.getY();

            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if (distance <= radius) {
                result.add(player);
            }
        }
        return result;
    }

    public List<Node> findPath(Vector2i start, Vector2i goal) {
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);
        while (openList.size() > 0) {
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);
            if (current.tile.equals(goal)) {
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null) {
                    path.add(current);
                    current = current.parent;// Return
                }
                openList.clear();
                closedList.clear();
                return path;
            }
            openList.remove(current);
            closedList.add(current);
            for (int i = 0; i < 9; i++) {
                if (i == 4) continue;
                int x = current.tile.getX();
                int y = current.tile.getY();
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile at = getTile(x + xi, y + yi);
                if (at == null) continue;
                if (at.solid()) continue;
                Vector2i a = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);
                if (vecInList(closedList, a) && gCost >= node.gCost) continue;
                if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
            }
        }
        closedList.clear();
        return null;
    }

    private boolean vecInList(List<Node> list, Vector2i vector) {
        for (Node n : list) {
            if (n.tile.equals(vector)) return true;
        }
        return false;
    }

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void add(Entity e) {
        e.init(this);

        if (e instanceof Player) {
            players.add((Player) e);
        } else if (e instanceof Projectile) {
            projectiles.add((Projectile) e);
        } else if (e instanceof NPC) {
            npcs.add((NPC) e);
        } else if (e instanceof Mob) {
            enemies.add((Mob) e);
        } else if (e instanceof Particle) {
            particles.add((Particle) e);
        } else if (e instanceof Item) {
            items.add((Item) e);
        }
    }

    private void remove() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isRemoved()) players.remove(i);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).isRemoved()) projectiles.remove(i);
        }
        for (int i = 0; i < npcs.size(); i++) {
            if (npcs.get(i).isRemoved()) npcs.remove(i);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isRemoved()) enemies.remove(i);
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).isRemoved()) particles.remove(i);
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isRemoved()) items.remove(i);
        }
    }

    public void update() {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
        for (int i = 0; i < npcs.size(); i++) {
            npcs.get(i).update();
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).update();
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update();
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).update();
        }

        remove();
    }

    public void render(Screen screen) {
        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width + 16) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                Tile tile = getTile(x, y);
                tile.render(x, y, screen);
            }
        }
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(screen);
        }

        for (int i = 0; i < npcs.size(); i++) {
            npcs.get(i).render(screen);
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).render(screen);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(screen);
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).render(screen);
        }
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).render(screen);
        }

    }

    public boolean tileCollision(int x, int y, int xOffset, int yOffset, int size) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            int xt = (x - c % 2 * size + xOffset) >> 4;
            int yt = (y - c / 2 * size + yOffset) >> 4;
            if (getTile((int) xt, (int) yt) instanceof WallTile) {
                xt = (x - c % 2 * size + xOffset + 2) >> 4;
                yt = (y - c / 2 * size + yOffset + 40) >> 4;
            } else if (getTile((int) xt, (int) yt).solid()) solid = true;
        }
        return solid;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;

        // Test =============================
        // if (tiles[x + y * width] == 0xFF000000) return Tile.grass;
        // ===== DUNGEON TYPE ONE COLOR ===============
        if (tiles[x + y * width] == Tile.col_portal) return Tile.portal;
        if (tiles[x + y * width] == Tile.col_d1_floor_01) return Tile.d1_floor_01;
        if (tiles[x + y * width] == Tile.col_d1_floor_02) return Tile.d1_floor_02;
        if (tiles[x + y * width] == Tile.col_d1_floor_03) return Tile.d1_floor_03;
        if (tiles[x + y * width] == Tile.col_d1_floor_04) return Tile.d1_floor_04;

        if (tiles[x + y * width] == Tile.col_d1_wall_02_top) return Tile.d1_wall_02_top;
        if (tiles[x + y * width] == Tile.col_d1_wall_02_bot) return Tile.d1_wall_02_bot;
        if (tiles[x + y * width] == Tile.col_d1_wall_03_top) return Tile.d1_wall_03_top;
        if (tiles[x + y * width] == Tile.col_d1_wall_03_bot) return Tile.d1_wall_03_bot;

        if (tiles[x + y * width] == Tile.col_d1_wall_corner_01) return Tile.d1_wall_corner_01;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_02) return Tile.d1_wall_corner_02;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_04) return Tile.d1_wall_corner_04;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_05) return Tile.d1_wall_corner_05;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_06) return Tile.d1_wall_corner_06;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_07) return Tile.d1_wall_corner_07;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_08) return Tile.d1_wall_corner_08;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_10) return Tile.d1_wall_corner_10;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_11) return Tile.d1_wall_corner_11;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_13) return Tile.d1_wall_corner_13;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_14) return Tile.d1_wall_corner_14;
        if (tiles[x + y * width] == Tile.col_d1_wall_corner_16) return Tile.d1_wall_corner_16;

        if (tiles[x + y * width] == Tile.col_d1_rug_corner_01) return Tile.d1_rug_corner_01;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_02) return Tile.d1_rug_corner_02;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_03) return Tile.d1_rug_corner_03;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_04) return Tile.d1_rug_corner_04;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_05) return Tile.d1_rug_corner_05;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_06) return Tile.d1_rug_corner_06;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_07) return Tile.d1_rug_corner_07;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_08) return Tile.d1_rug_corner_08;
        if (tiles[x + y * width] == Tile.col_d1_rug_corner_09) return Tile.d1_rug_corner_09;

        // ==================== ROOM FOR TESTING TILES =========================================
        if (tiles[x + y * width] == Tile.col_tt_01) return Tile.test_tile_01;

        else
            return Tile.voidTile;
    }
}
