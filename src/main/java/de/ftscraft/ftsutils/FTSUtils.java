package de.ftscraft.ftsutils;

import de.ftscraft.ftsutils.items.ItemBuilder;
import de.ftscraft.ftsutils.uuidfetcher.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class FTSUtils extends JavaPlugin {

    private static FTSUtils instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        ItemBuilder.initSignKey();
        getCommand("utilstest").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("ftsutils.test"))
            return true;
        if(label.equals("utilstest")) {
            if(args[0].equals("uuid")) {
                UUID uuid = UUIDFetcher.getUUID(args[1]);

                sender.sendMessage(uuid.toString());
                System.out.println(uuid);
            } else if(args[0].equals("name")) {
                String name = UUIDFetcher.getName(args[1]);
                sender.sendMessage(name);
                System.out.println(name);
            }
        }
        return true;
    }

    public static FTSUtils getInstance() {
        return instance;
    }

}
