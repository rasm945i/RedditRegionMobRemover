package dk.rasmusbendix.regionmobremover;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RemoveRegionManager {

    private final JavaPlugin plugin;
    @Getter private final List<RemoveRegion> removeRegionList;

    public RemoveRegionManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.removeRegionList = new ArrayList<>();
        loadRegions(plugin.getConfig());
    }

    public void reload() {
        removeRegionList.clear();
        loadRegions(plugin.getConfig());
    }

    public void loadRegions(FileConfiguration config) {

        ConfigurationSection section = config.getConfigurationSection("regions");
        if(section == null) {
            plugin.getLogger().warning("No 'regions' defined, make sure your config is correct.");
            return;
        }

        for(String worldName : section.getKeys(false)) {
            World world = Bukkit.getWorld(worldName);
            if(world == null) {
                plugin.getLogger().warning("Failed to find world named '" + worldName + "'!");
                continue;
            }

            RegionManager container = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
            if(container == null) {
                plugin.getLogger().warning("Failed to get WorldGuard region container for world '" + worldName + "'!");
                continue;
            }

            for(String regionName : section.getStringList(worldName)) {
                ProtectedRegion region = container.getRegion(regionName);
                if(region == null) {
                    plugin.getLogger().warning("Failed to find region '" + regionName + "' in world '" + worldName + "'!");
                    continue;
                }
                
                RemoveRegion removeRegion = new RemoveRegion(world, region, plugin);
                addRemoveRegion(removeRegion);
                
            }

        }
    }

    public void addRemoveRegion(RemoveRegion removeRegion) {
        this.removeRegionList.add(removeRegion);
    }

    public void removeEntitiesFromRegions() {
        removeRegionList.forEach(RemoveRegion::removeEntitiesInRegionAsync);
    }

}
