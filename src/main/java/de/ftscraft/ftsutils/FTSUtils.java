package de.ftscraft.ftsutils;

import de.ftscraft.ftsutils.items.ItemBuilder;
import de.ftscraft.ftsutils.uuidfetcher.UUIDFetcher;
import org.bukkit.plugin.java.JavaPlugin;

public final class FTSUtils extends JavaPlugin {

    private static FTSUtils instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        ItemBuilder.initSignKey();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FTSUtils getInstance() {
        return instance;
    }

}
