package diansa.dw.entity.player;

import diansa.dw.Game;
import diansa.dw.entity.Entity;
import diansa.dw.entity.item.Item;
import diansa.dw.entity.mob.Mob;
import diansa.dw.entity.npc.GeneralShopNPC;
import diansa.dw.events.Event;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.UI.UIActionListener;
import diansa.dw.graphics.UI.UIButton;
import diansa.dw.graphics.UI.UILabel;
import diansa.dw.graphics.UI.UIPanel;
import diansa.dw.graphics.UI.UIProgressBar;
import diansa.dw.graphics.UIManager;
import diansa.dw.graphics.playerUI.inventory.Inventory;
import diansa.dw.input.Keyboard;
import diansa.dw.input.Mouse;
import diansa.dw.level.Level;
import diansa.dw.util.Vector2i;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class Player extends Mob {

    protected Game game;
    protected Keyboard input;
    protected int time;

    protected static Inventory playerInventory;
    public static List<Item> equipment = new ArrayList<Item>();

    // ===== PLAYER STATS =====
    // 0 = Weapon Damage 6 = Neck Health 12 = Ring Health
    // 1 = Weapon CriticalDamage 7 = Neck Energy 13 = Ring Energy
    // 2 = Weapon CriticalHit 8 = Neck Damage 14 = Ring Damage
    // 3 = Weapon AttackSpeed 9 = Neck AttackSpeed 15 = Ring AttackSpeed
    // 4 = Weapon EnergyRegen 10 = Neck EnergyRegen 16 = Ring CriticaDamage
    // 5 = Weapon CriticalHit 11 = Neck HealthRegen 17 = Ring CriticalRate

    // 18 = Armor Health 24 = Gloves Health 30 = Boots Health
    // 19 = Armor Energy 25 = Gloves Energy 31 = Boots Energy
    // 20 = Armor EnergyRegen 26 = Gloves Damage 32 = Boots EnergyRegen
    // 21 = Armor HealthRegen 27 = Gloves CriticalHit 33 = Boots HealthRegen
    // 22 = Armor Luck 28 = Gloves AttackSpeed 34 = Boots MovementSpeed
    // 23 = Armor CriticalDamage 29 = Gloves CriticalDamage 35 = Boots Luck
    public static int[] equipmentStats;
    protected static int playerLevel = 1;
    protected static double damage = 10D;
    protected static int critRate = 10;
    protected static int critDamage = 50;
    public static double maxHealth = 100D;
    public static int maxEnergy = 50;
    protected static double baseSpeed = 1.5;
    protected static double movementSpeed = 0;
    protected static int attackSpeed = 100;
    protected static int energyRegen = 2;
    protected static int healthRegen = 5;
    public static int luck = 0;

    public static int playerHealth = 100;
    public static int playerLevelProgress = 0;
    public static int playerEnergy = 50;
    protected static int playerMoney = 0;

    // ========================
    protected UIManager ui;
    protected UIPanel panel;
    protected UIPanel characterPanel;
    protected UIPanel inventoryPanel;

    protected UILabel healthLabel;
    protected UILabel energyLabel;
    protected UILabel damageLabel;
    protected UILabel critRateLabel;
    protected UILabel critDamageLabel;
    protected UILabel movementSpeedLabel;
    protected UILabel attackSpeedLabel;

    protected UIProgressBar healthBar;
    protected UIProgressBar levelBar;
    protected UIProgressBar energyBar;

    protected UILabel levelLabel;

    public static boolean characterMenuIsOpen = false;
    public static boolean inventoryIsOpen = false;
    public static boolean resetOptionIsOpen = false;
    public static boolean multiShot = false;

    protected boolean shooting = false;

    public static boolean isDead = false;

    protected enum CharClass {
        GUNSLINGER, ARCHER, FLAMETHROWER;
    }

    protected static CharClass charClass;

    public Player(Game game, int x, int y, Keyboard input) {
        this.x = x;
        this.y = y;
        this.input = input;
        this.game = game;
        direction = Direction.DOWN;

        setMenu();
    }

    public static Inventory getPlayerInventory() {
        return playerInventory;
    }

    public static void addMoney(int money) {
        playerMoney += money;
    }

    public void onEvent(Event event) {
    }

    public void setMenu() {
        ui = Game.getUIManager();

        panel = (UIPanel) new UIPanel(new Vector2i(0, 0), new Vector2i(0, 0));
        ui.addPanel(panel);

        healthBar = (UIProgressBar) new UIProgressBar(new Vector2i(20, 20), new Vector2i(160, 20)).setColor(0xA8A8A8);
        healthBar.setForegroundColor(0xFF0000);
        panel.addComponent(healthBar);

        levelBar = (UIProgressBar) new UIProgressBar(new Vector2i(20, 45), new Vector2i(160, 20)).setColor(0xA8A8A8);
        levelBar.setForegroundColor(0x3A53A5);
        panel.addComponent(levelBar);

        energyBar = (UIProgressBar) new UIProgressBar(new Vector2i(20, 70), new Vector2i(160, 20)).setColor(0xA8A8A8);
        energyBar.setForegroundColor(0xE2AA00);
        panel.addComponent(energyBar);

        levelLabel = new UILabel(new Vector2i(20, 60), "Lvl: " + getPlayerLevel());
        levelLabel.setColor(0x000000);
        levelLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        panel.addComponent(levelLabel);
    }

    protected void move() {
        if (!GeneralShopNPC.shopIsOpen) {
            double xa = 0, ya = 0;
            if (input.up) {
                ya -= movementSpeed; // TODO: make a variable for this
                direction = Direction.UP;
            } else if (input.down) {
                ya += movementSpeed;
                direction = Direction.DOWN;
            }
            if (input.left) {
                xa -= movementSpeed;
                direction = Direction.UP_LEFT;
            } else if (input.right) {
                xa += movementSpeed;
                direction = Direction.UP_RIGHT;
            }

            if (xa != 0 && ya != 0) {
                xa *= 0.707;
                ya *= 0.707;
            }

            if (xa != 0 || ya != 0) {
                move(xa, ya);
                walking = true;
            } else {
                walking = false;
            }
        }
    }

    protected void inputHandler() {
        if (!input.character && !input.action && !input.inventory && actionPerformed) {
            if (shouldTeleport) {
                if (!resetOptionIsOpen && Entity.world > 0) {
                    System.out.println("Reset???");
                    if (Level.currentLevel != null) {
                        openResetOptionMenu();
                    } else {
                        game.changeLevel(world);
                    }
                }
                if (!resetOptionIsOpen && Entity.world == 0) {
                    game.changeLevel(world);
                }
                if (inventoryIsOpen) {
                    game.removeLayer(playerInventory);
                    inventoryIsOpen = false;
                }
                if (characterMenuIsOpen) {
                    ui.removePanel(characterPanel);
                    characterMenuIsOpen = false;
                }
                shouldTeleport = false;
            }
            actionPerformed = false;
        }
        if (input.character && !actionPerformed) {
            if (!characterMenuIsOpen) {
                characterMenuIsOpen = true;
            } else if (characterMenuIsOpen) {
                characterMenuIsOpen = false;
            }
            actionPerformed = true;
        }
        if (input.inventory && !actionPerformed) {
            if (!inventoryIsOpen) {
                game.addLayer(playerInventory);
                inventoryIsOpen = true;
            } else if (inventoryIsOpen) {
                game.removeLayer(playerInventory);
                inventoryIsOpen = false;
            }
            actionPerformed = true;
        }

    }

    public void openInventory() {
        game.addLayer(playerInventory);
        inventoryIsOpen = true;
    }

    protected void openResetOptionMenu() {
        resetOptionIsOpen = true;
        UIPanel panel = new UIPanel(new Vector2i(Game.width * Game.scale / 2 - 100, Game.height * Game.scale / 2 - 100),
                new Vector2i(0, 0), true, "/texture/reset.png");
        ui.addPanel(panel);
        UIButton reset = new UIButton(new Vector2i(24, 110), new Vector2i(71, 50), new UIActionListener() {
            public void perform() {
                Level.currentLevel = null;
                resetOptionIsOpen = false;
                shouldTeleport = false;
                game.changeLevel(world);
                System.out.println("Anzahl Spieler: " + level.players.size());
                ui.removePanel(panel);
                return;
            }
        });

        UIButton cancel = new UIButton(new Vector2i(110, 110), new Vector2i(71, 50), new UIActionListener() {
            public void perform() {
                // shouldTeleport = false;
                resetOptionIsOpen = false;
                game.changeLevel(world);
                ui.removePanel(panel);
                return;
            }
        });
        reset.setColor(0x00FFFFFF);
        reset.setText("RESET");
        cancel.setText("CANCEL");
        panel.addComponent(reset);
        panel.addComponent(cancel);
    }

    protected Direction checkPosition() {
        if (!shooting) return direction;
        if (Mouse.getY() >= (Game.height) * Game.scale / 2 && Mouse.getX() + 32 <= (Game.width) * Game.scale / 2) {
            direction = Direction.DOWN_LEFT;
            return direction;
        } else if (Mouse.getY() <= (Game.height) * Game.scale / 2 && Mouse.getX() + 32 < (Game.width) * Game.scale / 2) {
            direction = Direction.UP_LEFT;
            return direction;
        } else if (Mouse.getY() + 16 >= (Game.height) * Game.scale / 2
                && Mouse.getX() - 32 > (Game.width) * Game.scale / 2) {
            direction = Direction.DOWN_RIGHT;
            return direction;
        } else if (Mouse.getY() + 16 <= (Game.height) * Game.scale / 2
                && Mouse.getX() - 32 >= (Game.width) * Game.scale / 2) {
            direction = Direction.UP_RIGHT;
            return direction;
        } else if (Mouse.getY() <= Game.getWindowHeight() / 2
                && (Mouse.getX() + 32 >= Game.getWindowWidth() || Mouse.getX() - 32 <= Game.getWindowWidth())) {
            direction = Direction.UP;
            return direction;
        } else {
            direction = Direction.DOWN;
            return direction;
        }
    }

    public void updateStats() {
    }

    public static int getPlayerStats(Stats stats) {
        switch (stats) {
            case DAMAGE:
                return (int) damage;
            case CRITICAL_RATE:
                return critRate;
            case CRITICAL_DAMAGE:
                return critDamage;
            case ATTACK_SPEED:
                return attackSpeed;
        }
        return 0;
    }

    public void addItem(Item item) {
        playerInventory.addItemToSlot(item);
    }

    public void update() {
    }

    public void render(Screen screen) {
    }

    public static int getMoney() {

        return playerMoney;
    }

    public static int getPlayerLevel() {
        return playerLevel;
    }

    public static void setPlayerLevel(int playerLevel) {
        Player.playerLevel = playerLevel;
    }

}
