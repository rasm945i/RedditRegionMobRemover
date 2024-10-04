package dk.rasmusbendix.regionmobremover.rules;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class MonsterRemoveRule implements RemoveRule {

    @Override
    public boolean isRemovableEntity(Entity entity) {
        return entity instanceof Monster;
    }

}
