package diansa.dw.entity.spawner;

import diansa.dw.entity.particle.Particle;
import diansa.dw.graphics.Sprite;
import diansa.dw.level.Level;

public class ParticleSpawner extends Spawner {

    @SuppressWarnings("unused")
    private int duration;
    @SuppressWarnings("unused")
    private Sprite sprite;

    public ParticleSpawner(int x, int y, int duration, int amount, Level level, Sprite sprite) {
        super(x, y, Type.PARTICLE, amount, level);
        this.duration = duration;
        this.sprite = sprite;
        for (int i = 0; i < amount; i++) {
            level.add(new Particle(x, y, duration, sprite));
        }
    }
}
