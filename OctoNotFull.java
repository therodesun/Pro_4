import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OctoNotFull extends Octo{


    public OctoNotFull(String id, Point position, List<PImage> images, int actionPeriod, int animatedPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, actionPeriod, animatedPeriod, resourceLimit, resourceCount);
    }

    public static OctoNotFull createOctoNotFull(String id, int resourceLimit,
                                                Point position, int actionPeriod, int animationPeriod,
                                                List<PImage> images) {
        return new OctoNotFull(id, position, images, actionPeriod, animationPeriod,
                resourceLimit, 0);
    }


@Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            ActivateEntity octo = OctoFull.createOctoFull(this.id, this.resourceLimit,
                    this.position, this.actionPeriod, this.animationPeriod,
                    this.images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            octo.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (adjacent(this.position, target.position))
        {
            if (target instanceof InfectedFish) {
                this.resourceCount += 1;
                world.removeEntity(target);
                scheduler.unscheduleAllEvents(target);
                world.removeEntity(this);
                createInfectedOctor();

            }

            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.position);

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> notFullTarget = world.findNearest(this.position,
                Fish.class);

        if (!notFullTarget.isPresent() ||
                !moveTo(world,notFullTarget.get(), scheduler) ||
                !transform(world, scheduler, imageStore)) {
                scheduler.scheduleEvent(this,
                   ActivityAction.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

}
