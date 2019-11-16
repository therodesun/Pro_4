import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Trash extends ActivateEntity {
    public Trash(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public static Trash createTrash(String id, Point position, int actionPeriod,
                                      List<PImage> images) {
        return new Trash(id, position, images,
                actionPeriod);
    }


    /**
     * This method parse background arounds trash and create SeaTurle in random position
     * Edit method!!
     */
    @Override
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        /*
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent()) {
            ActivateEntity fish = Fish.createFish(Fish.FISH_ID_PREFIX + this.id,
                    openPt.get(), Fish.FISH_CORRUPT_MIN +
                            Fish.rand.nextInt(Fish.FISH_CORRUPT_MAX - Fish.FISH_CORRUPT_MIN),
                    imageStore.getImageList(Fish.FISH_KEY));
            world.addEntity(fish);
            fish.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                ActivityAction.createActivityAction(this, world, imageStore),
                this.actionPeriod);

         */
    }

}
