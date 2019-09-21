package diansa.dw.entity.npc;

import diansa.dw.Game;
import diansa.dw.entity.Entity;
import diansa.dw.entity.item.Item.ItemRarity;
import diansa.dw.entity.item.Neck;
import diansa.dw.entity.item.Ring;
import diansa.dw.entity.player.Player;
import diansa.dw.entity.spawner.ParticleSpawner;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.Sprite;

public class NPC extends Entity {

    protected Sprite sprite;
    protected Player player;

    public void dropChestLoot() {
        int drop = random.nextInt(2);
        int rarint = random.nextInt(3);
        ItemRarity rarity;
        if (rarint == 0) rarity = ItemRarity.NORMAL;
        else if (rarint == 1) rarity = ItemRarity.MAGIC;
        else if (rarint == 2) rarity = ItemRarity.RARE;
        else rarity = ItemRarity.UNIQUE;
        switch (drop) {
            case 0:
                Neck neck = new Neck((int) x, (int) y, rarity);
                level.add(neck);
                level.add(new ParticleSpawner((int) x, (int) y, 40, 50, level, Sprite.rare_drop));
                if (neck.itemRarity == ItemRarity.RARE) {
                    Game.sounds[3].play();
                }
                break;
            case 1:
                Ring ring = new Ring((int) x, (int) y, rarity);
                level.add(ring);
                level.add(new ParticleSpawner((int) x, (int) y, 40, 50, level, Sprite.epic_drop));
                if (ring.itemRarity == ItemRarity.RARE) {
                    Game.sounds[3].play();
                }
                break;
            default:
                break;
        }
    }

    public void update() {
        if (player == null && level.getClientPlayer() != null) {
            System.out.println("Npc sees player");
            player = level.getClientPlayer();
        }
    }

    public void render(Screen screen) {

    }

}
