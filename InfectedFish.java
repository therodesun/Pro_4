import processing.core.PImage;

import java.util.List;

public class InfectedFish extends Fish implements Infected

{
    public InfectedFish(String id, Point position, List<PImage> images, int actionPeriod, int animatedPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod);
    }

    @Override
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Crab crab = Crab.createCrab(this.id + Crab.CRAB_ID_SUFFIX,
                pos, this.actionPeriod / Crab.CRAB_PERIOD_SCALE,
                Crab.CRAB_ANIMATION_MIN +
                        rand.nextInt(Crab.CRAB_ANIMATION_MAX - Crab.CRAB_ANIMATION_MIN),
                imageStore.getImageList(Crab.CRAB_KEY));

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

    @Override
    public void infectOther(Entity target, ImageStore imagestore) {

    }
}
