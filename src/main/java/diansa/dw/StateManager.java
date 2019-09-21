package diansa.dw;

import diansa.dw.events.Event;
import diansa.dw.graphics.Screen;
import diansa.dw.graphics.layers.Layer;

public class StateManager {

    private Layer activeState;

    StateManager() {
        System.out.println("StateManager created!");
    }

    public void addState(Layer state) {
        activeState = state;
    }

    public void onEvent(Event event) {
        if (activeState != null)
            activeState.onEvent(event);
    }

    public void update() {
        if (activeState != null) activeState.update();
    }

    public void render(Screen screen) {
        if (activeState != null) activeState.render(screen);
    }
}
