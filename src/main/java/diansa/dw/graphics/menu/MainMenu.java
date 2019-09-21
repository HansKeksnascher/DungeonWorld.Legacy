package diansa.dw.graphics.menu;

import diansa.dw.Game;
import diansa.dw.events.Event;
import diansa.dw.events.EventListener;
import diansa.dw.graphics.UI.UIButton;
import diansa.dw.graphics.UI.UIPanel;
import diansa.dw.graphics.UIManager;
import diansa.dw.input.Mouse;
import diansa.dw.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

public class MainMenu extends Menu implements EventListener {

    private UIManager mainMenuUI;
    private int scale = Game.scale;

    public MainMenu(Game game, Mouse mouse) {
        this.mouse = mouse;
        this.game = game;
        mainMenuUI = Game.getUIManager();

        UIPanel panel = new UIPanel(new Vector2i(0, 0), new Vector2i(0, 0), true, "/texture/title.png");
        mainMenuUI.addPanel(panel);

        // UIButton start = new UIButton(new Vector2i(Game.width * scale / 2 -
        // 75, Game.height * scale / 2), new Vector2i(150, 40), new
        // UIActionListener() {
        // public void perform() {
        // game.initStart();
        // Game.gameStartInit = true;
        // Game.getUIManager().removePanel(panel);
        // return;
        // }
        // });

        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("fonts/start_button_idle.png");
            UIButton start = new UIButton(new Vector2i(Game.width * scale / 2 - 75, Game.height * scale / 2),
                    ImageIO.read(stream), () -> {
                game.initStart();
                Game.gameStartInit = true;
                Game.getUIManager().removePanel(panel);
            });
            panel.addComponent(start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEvent(Event event) {
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
    }
}
