package dk.rasmusbendix.regionmobremover.rules;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;

public class AnimalRemoveRule implements RemoveRule {

    @Override
    public boolean isRemovableEntity(Entity entity) {
        return entity instanceof Animals;
    }

}
