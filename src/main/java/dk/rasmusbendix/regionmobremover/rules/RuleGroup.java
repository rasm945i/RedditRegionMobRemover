package dk.rasmusbendix.regionmobremover.rules;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class RuleGroup implements RemoveRule {

    @Getter private final String id;
    private final List<RemoveRule> rules;

    public RuleGroup(String id) {
        this(id, new ArrayList<>());
    }

    public RuleGroup(String id, List<RemoveRule> rules) {
        this.id = id;
        this.rules = rules;
    }

    public static RuleGroup fromConfig(String name, ConfigurationSection section) {
        RuleGroup group = new RuleGroup(name);

        if(section.getBoolean("remove-monsters", false)) {
            group.addRule(new MonsterRemoveRule());
        }
        if(section.getBoolean("remove-animals", false)) {
            group.addRule(new AnimalRemoveRule());
        }
        if(section.getBoolean("remove-transport", false)) {
            group.addRule(new VehicleRemoveRule());
        }

        if(!section.contains("also-remove")) {
            return group;
        }

        CustomRule custom = CustomRule.fromStringList(false, section.getStringList("also-remove"));
        group.addRule(custom);
        return group;

    }

    public void addRule(RemoveRule rule) {
        rules.add(rule);
    }

    @Override
    public boolean isRemovableEntity(Entity entity) {
        for(RemoveRule rule : rules) {
            if(rule.isRemovableEntity(entity)) {
                return true;
            }
        }
        return false;
    }

}
