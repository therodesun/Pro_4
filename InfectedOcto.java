import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class InfectedOcto extends OctoFull implements Infected{

    public InfectedOcto(String id, Point position, List<PImage> images, int actionPeriod, int animatedPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod, animatedPeriod, resourceLimit, resourceCount);
    }

    /**
     * This method create a new infected-version of entity target
     * @param target is the nearest entity that will get infected
     */
    @Override
    public void infectOther(Entity target, ImageStore imageStore) {

    }

    /**
     * Override from Octofull. InfectedOcto will still go to entity but will not transform to unfull.
     * Instead, will disappear and create quake
     */
    @Override
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(position,
                Atlantis.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler)) {
            //at atlantis trigger animation
            ((ActivateEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //Edit this part of code to remove itself and create quake at its previous position
            //transform to unfull
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    ActivityAction.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }
}
