package diansa.dw.graphics;

public class Font {

    private static SpriteSheet font = new SpriteSheet("/fonts/myfont.png", 8);
    private static Sprite[] characters = Sprite.split(font);

    private static String charIndex = "ABCDEFGH" + //
            "IJKLMNOP" + //
            "QRSTUVWX" + //
            "YZ012345" + //
            "6789+-%:";

    public Font() {

    }

    public void render(int x, int y, String text, Screen screen) {
        int xOffset = 0;
        int line = 0;
        for (int i = 0; i < text.length(); i++) {
            xOffset += 8;
            int yOffset = 0;
            char currentChar = text.charAt(i);
            if (currentChar == '\n') {
                xOffset = 0;
                line++;
            }
            int index = charIndex.indexOf(currentChar);
            if (index == -1) continue;
            screen.renderSprite(x + xOffset, y + line * 11 + yOffset, characters[index], false);
        }
    }

}
