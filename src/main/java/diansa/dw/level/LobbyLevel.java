package diansa.dw.level;

import diansa.dw.entity.mob.Dummy;
import diansa.dw.entity.npc.GeneralShopNPC;
import diansa.dw.entity.npc.Portal;
import diansa.dw.entity.npc.TreasureChest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LobbyLevel extends Level {

    public LobbyLevel(String path) {
        super(path);
    }

    @Override
    protected void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(LobbyLevel.class.getResource(path));
            int w = width = image.getWidth();
            int h = height = image.getHeight();
            tiles = new int[w * h];
            image.getRGB(0, 0, w, h, tiles, 0, w);
        } catch (IOException e) {
            System.out.println("Could not load lobby!");
        }
        add(new Dummy(30, 10));
        add(new Portal(61, 7, 1));
        add(new Portal(64, 4, 2));
        add(new Portal(67, 7, 3));
        add(new Portal(73, 7, 4));
        add(new Portal(76, 4, 5));
        add(new Portal(79, 7, 6));
        add(new Portal(85, 7, 7));
        add(new Portal(88, 4, 8));
        add(new Portal(91, 7, 9));
        add(new Portal(100, 5, 10));

        add(new TreasureChest(3, 9, true));
        add(new TreasureChest(7, 9, true));
        add(new TreasureChest(11, 9, true));
        add(new TreasureChest(15, 9, true));

        add(new GeneralShopNPC(40, 10));

        System.out.println("lobby level loaded");
    }

    protected void generateLevel() {
    }
}
