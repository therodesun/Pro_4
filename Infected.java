public interface Infected {
    /**
     * This method creates a new infected-version of target entity.
     * @param target the entity that we want to infect
     */
    void infectOther(Entity target, ImageStore imagestore);
}
