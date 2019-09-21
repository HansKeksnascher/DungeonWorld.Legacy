package diansa.dw.entity.player;

import diansa.dw.Game;
import diansa.dw.entity.item.Item.ItemRarity;
import diansa.dw.entity.item.KeyItem;
import diansa.dw.entity.item.Pistol;
import diansa.dw.entity.npc.GeneralShopNPC;
import diansa.dw.events.Event;
import diansa.dw.events.EventDispatcher;
import diansa.dw.events.EventListener;
import diansa.dw.events.types.MousePressedEvent;
import diansa.dw.events.types.MouseReleasedEvent;
import diansa.dw.graphics.AnimatedSprite;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.SpriteSheet;
import diansa.dw.graphics.playerUI.inventory.Inventory;
import diansa.dw.input.Keyboard;
import diansa.dw.input.Mouse;

import java.awt.event.MouseEvent;

public class Gunslinger extends Player implements EventListener {

    private AnimatedSprite stay_side = new AnimatedSprite(SpriteSheet.gs_stay_side, 32, 32, 3);
    private AnimatedSprite stay_up = new AnimatedSprite(SpriteSheet.gs_stay_up, 32, 32, 3);
    private AnimatedSprite stay_down = new AnimatedSprite(SpriteSheet.gs_stay_down, 32, 32, 3);
    private AnimatedSprite stay_upside = new AnimatedSprite(SpriteSheet.gs_stay_upside, 32, 32, 3);

    private AnimatedSprite move_side = new AnimatedSprite(SpriteSheet.gs_move_side, 32, 32, 3);
    private AnimatedSprite move_up = new AnimatedSprite(SpriteSheet.gs_move_up, 32, 32, 3);
    private AnimatedSprite move_down = new AnimatedSprite(SpriteSheet.gs_move_down, 32, 32, 3);
    private AnimatedSprite move_upside = new AnimatedSprite(SpriteSheet.gs_move_upside, 32, 32, 3);

    private AnimatedSprite animSprite = stay_side;

    private int fireRate = 0;
    private int timer = 0;

    public Gunslinger(Game game, int x, int y, Keyboard input) {
        super(game, x, y, input);
        isDead = false;
        classID = 1;
        charClass = CharClass.GUNSLINGER;
        sprite = animSprite;
        if (playerInventory == null) {
            playerInventory = new Inventory(this);
        }
        if (equipment.isEmpty()) {
            for (int i = 0; i < 6; i++) {
                equipment.add(null);
            }
        }
        if (equipmentStats == null) {
            equipmentStats = new int[36];
            for (int i = 0; i < 10; i++) {
                equipmentStats[i] = 0;
            }
        }
        playerInventory.addItemToSlot(new Pistol(ItemRarity.UNIQUE, 20));
        playerInventory.addItemToSlot(new KeyItem());
        // class stats
        playerHealth = (int) maxHealth;
        playerEnergy = maxEnergy;
        playerLevel = 1;
        playerLevelProgress = 0;
        updateStats();
    }

    @Override
    public void onEvent(Event event) {
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
    }

    public boolean onMousePressed(MousePressedEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            shooting = true;
            return true;
        }
        return false;
    }

    public boolean onMouseReleased(MouseReleasedEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            shooting = false;
            return true;
        }
        return false;
    }

    private void updateShooting() {
        if (!shooting || fireRate > 0 || playerEnergy < (3 * playerLevel)) return;

        double dx = Mouse.getX() - Game.getWindowWidth() / 2;
        double dy = Mouse.getY() - Game.getWindowHeight() / 2;
        double dir = Math.atan2(dy, dx);
        shoot(x + (Math.cos(dir) * 18), y + 5 + (Math.tan(dir) * Math.cos(dir) * 18), dir);
        if (multiShot) {
            shoot(x + (Math.cos(dir) * 18), y + 5 + (Math.tan(dir) * Math.cos(dir) * 18), dir - Math.PI / 20);
            shoot(x + (Math.cos(dir) * 18), y + 5 + (Math.tan(dir) * Math.cos(dir) * 18), dir + Math.PI / 20);
        }
        playerEnergy -= 3 * playerLevel;
        fireRate = attackSpeed;
    }

    public void update() {
        if (isDead) return;
        //if(inventoryIsOpen) playerInventory.update();
        timer++;
        if (timer > 10000) {
            timer = 0;
        }
        if (timer % 120 == 0 && playerEnergy < maxEnergy) playerEnergy += energyRegen;
        if (timer % 120 == 0 && playerHealth < maxHealth) playerHealth += healthRegen;
        // ===== CHECK EQUIPMENT ===========================

        // =================================================
        if (hitted) {
            if (damaged) {
                hitted = false;
                damaged = false;
            }
        }
        if (playerHealth <= 0) {
            playerHealth = 0;
            isDead = true;
            playerInventory = null;
            game.changeLevel(0);
        }
        if (fireRate > 0) fireRate--;
        animSprite.update();
        if (!resetOptionIsOpen) move();
        inputHandler();

        // ===== UPDATE UI ============================
        if (playerHealth > maxHealth) playerHealth = (int) maxHealth;
        double healthProgress = (double) playerHealth / (double) maxHealth;
        healthBar.setProgress(healthProgress);
        if (playerLevel < 100) {
            if (playerLevelProgress > (100 + (100.0 * ((getPlayerLevel() - 1) * 0.2)))) {
                setPlayerLevel(getPlayerLevel() + 1);
                updateStats();
                playerLevelProgress = 0;
                playerHealth = (int) maxHealth;
                levelLabel.setText("Lvl: " + getPlayerLevel());

            }
        }
        if (playerLevel < 100)
            levelBar.setProgress(playerLevelProgress / (100 + (100.0 * ((getPlayerLevel() - 1) * 0.2))));
        if (playerEnergy > maxEnergy) playerEnergy = maxEnergy;
        energyBar.setProgress((double) playerEnergy / (double) maxEnergy);

        if (!GeneralShopNPC.shopIsOpen && !resetOptionIsOpen) updateShooting();
    }

    public void updateStats() {
//		damage = 20 + (20.0 * ((getPlayerLevel() - 1) * 0.1)) + equipmentStats[0] + equipmentStats[6] + equipmentStats[7];
//		critRate = 10 + equipmentStats[1];
//		critDamage = 50 + equipmentStats[2];
//		maxHealth = 100 + (100.0 * ((getPlayerLevel() - 1) * 0.2)) + equipmentStats[3] + equipmentStats[8] + equipmentStats[9];
//		movementSpeed = 2 + equipmentStats[4];
//		attackSpeed = 60 + equipmentStats[5];
        System.out.println("==== Player Stats updatet! ====");
        damage = 20 + (20.0 * (playerLevel - 1) * 0.1) + equipmentStats[0] + equipmentStats[8] + equipmentStats[14] + equipmentStats[26];
        critDamage = (int) (50 + (equipmentStats[1] + equipmentStats[16] + equipmentStats[23] + equipmentStats[29]) * 0.1);
        critRate = (int) (10 + (equipmentStats[2] + equipmentStats[5] + equipmentStats[17] + equipmentStats[27]) * 0.1);
        attackSpeed = (int) (40 - (equipmentStats[3] + equipmentStats[9] + equipmentStats[15] + equipmentStats[28]) * 0.1);
        energyRegen = 2 + equipmentStats[4] + equipmentStats[10] + equipmentStats[20] + equipmentStats[32];
        maxHealth = 100 + 100.0 * ((playerLevel - 1) * 0.2) + equipmentStats[6] + equipmentStats[12] + equipmentStats[18] + equipmentStats[24] + equipmentStats[30];
        maxEnergy = 50 + equipmentStats[7] + equipmentStats[13] + equipmentStats[19] + equipmentStats[25] + equipmentStats[31];
        healthRegen = 5 + equipmentStats[11] + equipmentStats[21] + equipmentStats[33];
        luck = equipmentStats[22] + equipmentStats[35];
        movementSpeed = baseSpeed + equipmentStats[34] * 0.1;
    }

    public void render(Screen screen) {
        // TODO: rendering
        //if(inventoryIsOpen) playerInventory.render(screen);
        if (isDead) return;
        sprite = animSprite.getSprite();
        direction = checkPosition();

        if (direction == Direction.DOWN_LEFT) {
            if (walking) animSprite = move_side;
            else
                animSprite = stay_side;
            screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 1);
        }
        if (direction == Direction.UP_LEFT) {
            if (walking) animSprite = move_side;
            else
                animSprite = stay_side;
            screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 1);
        }
        if (direction == Direction.DOWN_RIGHT) {
            if (walking) animSprite = move_side;
            else
                animSprite = stay_side;
            screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
        }
        if (direction == Direction.UP_RIGHT) {
            if (walking) animSprite = move_side;
            else
                animSprite = stay_side;
            screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
        }
        if (direction == Direction.UP) {
            if (walking) animSprite = move_up;
            else
                animSprite = stay_up;
            screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
        }
        if (direction == Direction.DOWN) {
            //direction = Direction.DOWN;
            if (walking) animSprite = move_down;
            else
                animSprite = stay_down;
            screen.renderMob((int) (x - 16), (int) (y - 16), sprite, 32);
        }

        // TODO: move to character screen class render method
        if (characterMenuIsOpen) {
            screen.renderSprite(10, 65, SpriteSheet.character_screen, false);
            Inventory.font.render(18, 186, "DMG:" + (int) damage, screen);
            Inventory.font.render(18, 206, "CRIT:" + critRate, screen);
            Inventory.font.render(18, 226, "MSPD:" + (int) (movementSpeed * 10), screen);
            Inventory.font.render(18, 246, "HREG:" + healthRegen, screen);
            Inventory.font.render(85, 186, "EREG:" + energyRegen, screen);
            Inventory.font.render(85, 206, "CDMG:" + critDamage, screen);
            Inventory.font.render(85, 226, "ASPD:" + attackSpeed, screen);
            Inventory.font.render(85, 246, "LUCK:" + luck, screen);
            Inventory.font.render(125, 99, "" + getPlayerLevel(), screen);
            Inventory.font.render(116, 119, "" + (int) maxHealth, screen);
            Inventory.font.render(116, 139, "" + maxEnergy, screen);
        }

    }

}
