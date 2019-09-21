package diansa.dw.level;

import diansa.dw.entity.mob.ArcherMob;
import diansa.dw.entity.npc.Portal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dungeon_1_3 extends Level {
    public Dungeon_1_3(String path) {
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
        add(new Portal(40, 10, 0));
        for (int i = 0; i < 20; i++) {
            add(new ArcherMob(60, 10));
        }
        System.out.println("Dungeon 1_3 initialized");

    }

    @Override
    protected void generateLevel() {

    }
}
