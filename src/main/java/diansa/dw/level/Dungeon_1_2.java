package diansa.dw.level;

import diansa.dw.entity.mob.ArcherMob;
import diansa.dw.entity.mob.MadIgor;
import diansa.dw.entity.npc.Portal;
import diansa.dw.entity.npc.Teleport;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dungeon_1_2 extends Level {
    public Dungeon_1_2(String path) {
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
        add(new Portal(179, 99, 0));
        add(new Teleport(36, 54, 78, 37));
        add(new Teleport(78, 37, 36, 54));

        // ENEMIES
        add(new ArcherMob(142, 101));
        add(new ArcherMob(144, 114));
        add(new ArcherMob(128, 117));
        add(new ArcherMob(117, 118));
        add(new ArcherMob(89, 123));
        add(new ArcherMob(98, 115));
        add(new ArcherMob(88, 101));
        add(new ArcherMob(98, 93));
        add(new ArcherMob(127, 89));
        add(new ArcherMob(115, 100));
        add(new ArcherMob(115, 91));

        add(new MadIgor(78, 25));
        //
        System.out.println("Dungeon 1_2 initialized");

    }

    @Override
    protected void generateLevel() {

    }
}
