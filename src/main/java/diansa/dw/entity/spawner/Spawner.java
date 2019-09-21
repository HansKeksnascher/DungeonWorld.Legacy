package diansa.dw.entity.spawner;

import diansa.dw.entity.Entity;
import diansa.dw.level.Level;

public class Spawner extends Entity {

//	private List<Entity> entities = new ArrayList<Entity>();

    public enum Type {
        MOB, PARTICLE, ITEM;
    }

    @SuppressWarnings("unused")
    private Type type;

    public Spawner(int x, int y, Type type, int amount, Level level) {
        init(level);
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
