package dk.rasmusbendix.regionmobremover.rules;

import org.bukkit.entity.Entity;

public interface RemoveRule {

    boolean isRemovableEntity(Entity entity);

}
