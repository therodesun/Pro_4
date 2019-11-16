import java.util.Optional;

public class ActivityAction implements Action {
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = 0;
    }

    public static ActivityAction createActivityAction(Entity entity, WorldModel world,
                                              ImageStore imageStore)
    {
        return new ActivityAction(entity, world, imageStore);
    }


    @Override
    public void execute(EventScheduler scheduler) {
        if (entity instanceof OctoFull) {
            ((OctoFull) entity).execute(world, imageStore, scheduler);
        } else if (entity instanceof OctoNotFull) {
            ((OctoNotFull) entity).execute(world, imageStore, scheduler);
        } else if (entity instanceof Fish) {
            ((Fish) entity).execute(world, imageStore, scheduler);
        } else if (entity instanceof Crab) {
            ((Crab) entity).execute(world, imageStore, scheduler);
        } else if (entity instanceof Quake) {
            ((Quake) entity).execute(world, imageStore, scheduler);
        } else if (entity instanceof SGrass) {
            ((SGrass) entity).execute(world, imageStore, scheduler);
        } else if (entity instanceof Atlantis) {
            ((Atlantis) entity).execute(world, imageStore, scheduler);
        } else {
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            entity.getClass()));
        }
    }


}
