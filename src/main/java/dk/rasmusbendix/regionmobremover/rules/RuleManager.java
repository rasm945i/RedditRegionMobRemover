package dk.rasmusbendix.regionmobremover.rules;

import dk.rasmusbendix.regionmobremover.RegionMobRemoverPlugin;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class RuleManager {

    @Getter private final RegionMobRemoverPlugin plugin;
    private final Map<String, RemoveRule> rules;

    public RuleManager(RegionMobRemoverPlugin plugin) {
        this.plugin = plugin;
        this.rules = new HashMap<>();
        loadRules();
    }

    public void reloadRules() {
        rules.clear();
        loadRules();
    }

    public RemoveRule getRule(String name) {
        return rules.get(name);
    }

    public void loadRules() {

        // Even if 'remove-preset' doesn't exist, for some reason 'section' is not null?
        rules.put("default", new MonsterRemoveRule());
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("remove-preset");
        if(section == null) {
            return;
        }

        for(String key : section.getKeys(false)) {
            rules.put(key, RuleGroup.fromConfig(key, section.getConfigurationSection(key)));
        }

        plugin.getLogger().info("Loaded rule presets!");

    }

}
