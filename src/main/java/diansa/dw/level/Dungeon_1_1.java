package diansa.dw.level;

import diansa.dw.entity.mob.ArcherMob;
import diansa.dw.entity.mob.MadIgor;
import diansa.dw.entity.npc.Portal;
import diansa.dw.entity.npc.Teleport;
import diansa.dw.entity.npc.TreasureChest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dungeon_1_1 extends Level {

    public Dungeon_1_1(String path) {
        super(path);
    }

    @Override
    protected void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(DefaultLevel.class.getResource(path));
            int w = width = image.getWidth();
            int h = height = image.getHeight();
            tiles = new int[w * h];
            image.getRGB(0, 0, w, h, tiles, 0, w);
        } catch (IOException e) {
            System.out.println("Could not load level file!");
        }
        add(new Portal(110, 125, 0));

        add(new Teleport(150, 32, 51, 15));
        add(new Teleport(50, 15, 149, 32));
        add(new TreasureChest(132, 96, false));
        add(new TreasureChest(56, 123, false));

// ========== Treasure Room LEVEL =========================================
//		for(int y = 0; y < 4; y++){
//			for(int x = 0; x < 4; x++){
//				add(new TreasureChest(29 + x * 4, 18 + y * 4, false));
//			}
//		}
        int spawner = random.nextInt(3);
        if (spawner == 1) {
            add(new TreasureChest(56, 70, false));
        }
        add(new ArcherMob(118, 97));
        add(new ArcherMob(86, 97));
        add(new ArcherMob(57, 98));
        add(new ArcherMob(66, 107));
        add(new ArcherMob(91, 74));
        add(new ArcherMob(113, 70));
        add(new ArcherMob(102, 97));
        add(new ArcherMob(86, 104));
        add(new ArcherMob(64, 110));
        add(new ArcherMob(52, 72));
        add(new ArcherMob(59, 72));
        add(new ArcherMob(96, 76));
        add(new ArcherMob(137, 71));
        add(new ArcherMob(142, 93));
        add(new ArcherMob(120, 33));
        add(new ArcherMob(123, 43));

        add(new ArcherMob(114, 82));
        add(new ArcherMob(122, 84));
        add(new ArcherMob(123, 78));
        add(new ArcherMob(136, 69));
        add(new ArcherMob(116, 52));
        add(new ArcherMob(128, 54));

        add(new ArcherMob(50, 127));
        add(new ArcherMob(62, 127));
        add(new ArcherMob(36, 93));
        add(new ArcherMob(94, 40));
        add(new ArcherMob(99, 47));
        add(new ArcherMob(171, 103));
        add(new ArcherMob(172, 117));
        if (spawner == 2) add(new ArcherMob(180, 102)); // random mini-boss
        add(new ArcherMob(168, 67));
        add(new ArcherMob(194, 73));
        add(new MadIgor(28, 20));

        System.out.println("Dungeon 1_1 initialized");

    }

    @Override
    protected void generateLevel() {

    }
}
