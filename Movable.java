import processing.core.PImage;

import java.util.List;

public abstract class Movable extends AnimatedEntity {
    public Movable(String id, Point position, List<PImage> images, int actionPeriod, int animatedPeriod) {
        super(id, position, images, actionPeriod, animatedPeriod);
    }
    public boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) ||
                (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public abstract Point nextPosition(WorldModel world, Point destPos);


}
