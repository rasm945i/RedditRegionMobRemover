package dk.rasmusbendix.regionmobremover.rules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class VehicleRemoveRule implements RemoveRule {

    @Override
    public boolean isRemovableEntity(Entity entity) {
        return entity.getType() == EntityType.BOAT || entity.getType() == EntityType.MINECART;
    }

}
