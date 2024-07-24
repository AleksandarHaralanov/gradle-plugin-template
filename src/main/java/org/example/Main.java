package org.example;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Initialization logic (e.g., register listeners, commands)

        getServer().getLogger().info(String.format("[%s] v%s Enabled.", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        // Cleanup logic (e.g., save data, unregister listeners)

        getServer().getLogger().info(String.format("[%s] v%s Disabled.", getDescription().getName(), getDescription().getName()));
    }

    public static Main getInstance() {
        return instance;
    }
}