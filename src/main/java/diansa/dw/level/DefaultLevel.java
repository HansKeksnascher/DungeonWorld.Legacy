package diansa.dw.level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DefaultLevel extends Level {

    public DefaultLevel(String path) {
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

    }

    @Override
    protected void generateLevel() {

    }
}
