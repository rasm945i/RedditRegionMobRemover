package dk.rasmusbendix.regionmobremover;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class RegionMobRemoverPlugin extends JavaPlugin {

    private RemoveRegionManager removeRegionManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        removeRegionManager = new RemoveRegionManager(this);
        int delay = getConfig().getInt("settings.ticks-between-removal", 20);
        Bukkit.getScheduler().runTaskTimer(this, removeRegionManager::removeEntitiesFromRegions, delay*2L, delay);
        getServer().getPluginCommand("mobremover").setExecutor(new RemoverCommand(this));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
