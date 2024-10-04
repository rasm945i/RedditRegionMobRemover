package dk.rasmusbendix.regionmobremover.rules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRule implements RemoveRule {

    private final Set<EntityType> types;
    // If true, we remove everything except what is in types
    // If false, we remove everything in types
    private final boolean asWhitelist;

    public CustomRule(boolean asWhitelist) {
        this.types = new HashSet<>();
        this.asWhitelist = asWhitelist;
    }

    public void addRemovableEntity(EntityType type) {
        types.add(type);
    }

    public static CustomRule fromStringList(boolean asWhitelist, List<String> types) {
        CustomRule rule = new CustomRule(asWhitelist);
        for(String type : types) {
            try {
                EntityType entityType = EntityType.valueOf(type.toUpperCase());
                rule.addRemovableEntity(entityType);
            } catch (IllegalArgumentException e) {
                Bukkit.getServer().getLogger().warning(type + " is not a valid entity type!");
            }
        }
        return rule;
    }

    @Override
    public boolean isRemovableEntity(Entity entity) {
        if(asWhitelist) {
            return !types.contains(entity.getType());
        }
        return types.contains(entity.getType());
    }

}
