package dk.rasmusbendix.regionmobremover;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoverCommand implements CommandExecutor {

    private final RegionMobRemoverPlugin plugin;

    public RemoverCommand(RegionMobRemoverPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!sender.hasPermission("regionmobremover.admin")) {
            send(sender, "no-permission");
            return true;
        }

        if(args.length < 1) {
            send(sender, "show-args");
            return true;
        }

        if(args[0].equalsIgnoreCase("info")) {
            StringBuilder message = new StringBuilder();
            message.append(ChatColor.DARK_GREEN).append("Enabled regions that removes monsters:");
            for(RemoveRegion rr : plugin.getRemoveRegionManager().getRemoveRegionList()) {
                message.append("\r\n").append(ChatColor.GREEN).append(rr.getRegion().getId());
            }
            sender.sendMessage(message.toString());
            return true;
        }

        if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
            plugin.reloadConfig();
            plugin.getRuleManager().reloadRules();
            plugin.getRemoveRegionManager().reload();
            sender.sendMessage(ChatColor.YELLOW + "Reloaded regions from config!");
            return true;
        }

        send(sender, "show-args");
        return true;
    }

    private void send(CommandSender sender, String path) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("message." + path, "message." + path + " not found.")));
    }
}
