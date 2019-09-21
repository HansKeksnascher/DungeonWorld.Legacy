package diansa.dw;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import diansa.dw.entity.player.Gunslinger;
import diansa.dw.entity.player.Player;
import diansa.dw.events.Event;
import diansa.dw.events.EventListener;
import diansa.dw.graphics.MiniMap;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.graphics.UIManager;
import diansa.dw.graphics.layers.Layer;
import diansa.dw.graphics.menu.MainMenu;
import diansa.dw.graphics.menu.Menu;
import diansa.dw.input.Keyboard;
import diansa.dw.input.Mouse;
import diansa.dw.level.Level;
import diansa.dw.sound.Sound;

import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

public class Game implements Runnable, EventListener {
    private static final long serialVersionUID = 1L;

    public static int width = 450; /// 450
    public static int height = 300; /// 300
    public static int scale = 2;

    private JFrame frame;
    private Screen screen;
    public static Keyboard keyInput;
    public Mouse mouseInput;
    @SuppressWarnings("unused")
    private static Menu menu;
    private Level level;
    private Player player;
    private MiniMap miniMap;
    private int[] pixels;

    private static UIManager uiManager;
    public static Sound[] sounds = new Sound[10];
    public static boolean gameStartInit = false;

    // layer list
    private List<Layer> layerStack = new ArrayList<Layer>();

    public static StateManager sm = new StateManager();
    private GLCanvas canvas = null;
    private Panel panel = new Panel();
    private FPSAnimator animator = null;

    private long lastTime = System.nanoTime();
    private final double ns = 1_000_000_000.0 / 60.0;
    private double delta = 0;
    private int frames = 0;
    private int updates = 0;
    private long fpsTimer = System.currentTimeMillis();

    public Game() {
        loadSounds();
        screen = new Screen(width, height);
        frame = new JFrame();
        keyInput = new Keyboard();
        mouseInput = new Mouse(this);
        uiManager = new UIManager();
        miniMap = new MiniMap();
        setMenu(new MainMenu(this, mouseInput));
    }

    private void loadSounds() {
        sounds[0] = new Sound("sound/hit.wav");
        sounds[1] = new Sound("sound/shoot.wav");
        sounds[2] = new Sound("sound/pickup.wav");
        sounds[3] = new Sound("sound/legendary_drop.mp3");
        sounds[4] = new Sound("sound/equip.wav");
        sounds[5] = new Sound("sound/sold.wav");
        for (int i = 0; i < 6; i++) {
            sounds[i].setGain(0.1);
        }
    }

    public static int getWindowWidth() {
        return width * scale;
    }

    public static int getWindowHeight() {
        return height * scale;
    }

    public static UIManager getUIManager() {
        return uiManager;
    }

    // add + delete layers
    public void addLayer(Layer layer) {
        layerStack.add(layer);
    }

    public void removeLayer(Layer layer) {
        layerStack.remove(layer);
    }

    public void setMenu(Menu menu) {
        Game.menu = menu;
        if (menu != null) {
            menu.init(this, mouseInput);
        }
    }

    public void initStart() {
        level = Level.setLevel(0);
        sm.addState(level);
        // TODO: player selection
        player = new Gunslinger(this, Level.spawnX, Level.spawnY, keyInput);
        level.add(player);
    }

    public void changeLevel(int level) {
        if (Player.isDead) {
            player.remove();
            setMenu(new MainMenu(this, mouseInput));
            return;
        }
        this.level.players.remove(player);
        this.level = Level.setLevel(level);
        sm.addState(this.level);
        player.setX(Level.spawnX);
        player.setY(Level.spawnY);
        this.level.add(player);
    }

    public void onEvent(Event event) {
        if (gameStartInit && !player.isRemoved()) {
            for (int i = layerStack.size() - 1; i >= 0; i--) {
                layerStack.get(i).onEvent(event);
            }
        }
        sm.onEvent(event);
    }

    public synchronized void start() {
        animator.start();
    }

    public synchronized void stop() {
        animator.stop();
    }

    @Override
    public void run() {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        while (delta >= 1) {
            update();
            updates++;
            delta -= 1;
        }
        render();
        frames++;
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            fpsTimer += 1000;
            System.out.println("UPS: " + updates + "  FPS: " + animator.getFPS());
            frames = 0;
            updates = 0;
        }
    }

    public void update() {
        keyInput.update();
        uiManager.update();
        sm.update();
        if (gameStartInit && !player.isRemoved()) {
            for (Layer layer : layerStack) {
                layer.update();
            }
        }
    }

    public void render() {
        screen.clearScreen();
        if (gameStartInit && !player.isRemoved()) {
            int xScroll = (int) (player.getX() - screen.width / 2);
            int yScroll = (int) (player.getY() - screen.height / 2);
            level.setScroll(xScroll, yScroll);
            sm.render(screen);
            for (Layer layer : layerStack) {
                layer.render(screen);
            }
        }
        Graphics2D g = screen.image.createGraphics();
        g.scale(1.0 / scale, 1.0 / scale);
        if (level != null && player != null) miniMap.render(g, level, SpriteSheet.dungeon_1_1_map, player);
        uiManager.render(g);
        g.dispose();
    }

    public void initGL() {
        GLProfile profile = GLProfile.getGL2GL3();
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setDoubleBuffered(true);
        canvas = new GLCanvas(capabilities);
        canvas.setSize(getWindowWidth(), getWindowHeight());
        panel.setSize(getWindowWidth(), getWindowHeight());
        canvas.addGLEventListener(new GameHandler(this));
        animator = new FPSAnimator(canvas, 60);
        frame.getContentPane().add(canvas);
        frame.setSize(canvas.getSize());
        frame.setResizable(false);
        frame.setTitle("DungeonWorld");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        canvas.addKeyListener(keyInput);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
    }

    public Screen getScreen() {
        return screen;
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "false");
        System.setProperty("sun.java2d.noddraw", "true");
        System.setProperty("sun.awt.noerasebackground", "true");
        Game game = new Game();
        game.initGL();
        game.start();
    }
}
