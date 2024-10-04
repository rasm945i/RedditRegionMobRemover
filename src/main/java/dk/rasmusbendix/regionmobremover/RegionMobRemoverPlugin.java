package dk.rasmusbendix.regionmobremover;

import dk.rasmusbendix.regionmobremover.rules.RuleManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class RegionMobRemoverPlugin extends JavaPlugin {

    private RemoveRegionManager removeRegionManager;
    private BukkitTask task;
    private RuleManager ruleManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ruleManager = new RuleManager(this);
        removeRegionManager = new RemoveRegionManager(this);
        int delay = getConfig().getInt("settings.ticks-between-removal", 20);
        task = Bukkit.getScheduler().runTaskTimer(this, removeRegionManager::removeEntitiesFromRegions, delay*2L, delay);
        getServer().getPluginCommand("mobremover").setExecutor(new RemoverCommand(this));
    }

    @Override
    public void onDisable() {
        task.cancel();
    }

}
