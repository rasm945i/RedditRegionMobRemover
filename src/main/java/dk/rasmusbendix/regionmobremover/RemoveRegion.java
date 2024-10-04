package dk.rasmusbendix.regionmobremover;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dk.rasmusbendix.regionmobremover.rules.RemoveRule;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

@Getter
public class RemoveRegion {

    private final JavaPlugin plugin;
    private final ProtectedRegion region;
    private final Location regionCenter;
    private final double removeRadius;
    private final double removeHeight;
    private final RemoveRule ruleGroup;

    public RemoveRegion(World world, ProtectedRegion region, RemoveRule ruleGroup, JavaPlugin plugin) {
        this.region = region;
        this.plugin = plugin;
        this.ruleGroup = ruleGroup;
        this.regionCenter = calculateCenter(world, region);
        this.removeRadius = calculateRadius(region) + 2;
        this.removeHeight = calculateHeight(region);
    }

    private Location calculateCenter(World world, ProtectedRegion region) {
        double xCenter = getCenterPoint(region.getMaximumPoint().getX(), region.getMinimumPoint().getX());
        double yCenter = getCenterPoint(region.getMaximumPoint().getY(), region.getMinimumPoint().getY());
        double zCenter = getCenterPoint(region.getMaximumPoint().getZ(), region.getMinimumPoint().getZ());
        return new Location(world, xCenter, yCenter, zCenter);
    }

    private double getCenterPoint(double max, double min) {
        return max - ((max - min) / 2);
    }

    private double calculateRadius(ProtectedRegion region) {
        return Math.max(
                region.getMaximumPoint().getX() - region.getMinimumPoint().getX(),
                region.getMaximumPoint().getZ() - region.getMinimumPoint().getZ()
        ) / 2f;
    }

    private double calculateHeight(ProtectedRegion region) {
        return region.getMaximumPoint().getY() - region.getMinimumPoint().getY();
    }

    private Collection<Entity> getMonstersInRegion() {
        Collection<Entity> entities = regionCenter.getNearbyEntities(removeRadius, removeHeight, removeRadius);
        entities.removeIf(entity -> !isInsideRegion(entity.getLocation()) || !(entity instanceof Monster));
        return entities;
    }

    public boolean isInsideRegion(Location location) {
        return (location.getX() >= region.getMinimumPoint().getX() && location.getX() <= region.getMaximumPoint().getX()+1) &&
                (location.getY() >= region.getMinimumPoint().getY() && location.getY() <= region.getMaximumPoint().getY()) &&
                (location.getZ() >= region.getMinimumPoint().getZ() && location.getZ() <= region.getMaximumPoint().getZ()+1);
    }

    public void removeEntitiesInRegion() {
        getMonstersInRegion().forEach(entity -> {
            if(ruleGroup.isRemovableEntity(entity)) {
                entity.remove();
            }
        });
    }

}
