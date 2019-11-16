import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class InfectedSeaturtle extends Movable implements Infected {
        public static int InfectedSeaturtle;
     static final Random rand = new Random();

    public InfectedSeaturtle(String id, Point position, List<PImage> images, int actionPeriod, int animatedPeriod) {
        super(id, position, images, actionPeriod, animatedPeriod);

    }
    public static InfectedSeaturtle createInfectedseaTurtle(String id,
                                                Point position, int actionPeriod, int animationPeriod,
                                                List<PImage> images) {
        return new InfectedSeaturtle(id, position, images, actionPeriod, animationPeriod);
    }

    @Override
    public void infectOther(Entity target, ImageStore imagestore) {

    }
    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz,
                this.position.getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(),
                    this.position.getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.position;
            }
        }

        return newPos;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (adjacent(this.position, target.position))
        {

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
        Optional<Entity> fullTarget = world.findNearest(position,
                Trash.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler)) {
            //at atlantis trigger animation
            ((ActivateEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            Quake quake = Quake.createQuake(Quake.QUAKE_ID,this.position,imageStore.getImageList(Quake.QUAKE_KEY));
            world.addEntity(quake);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    ActivityAction.createActivityAction(this, world, imageStore),
                    this.actionPeriod);
        }
    }

}
