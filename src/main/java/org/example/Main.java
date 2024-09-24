package org.example;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import static org.example.util.LoggerUtil.logInfo;

public class Main extends JavaPlugin {

    private static PluginDescriptionFile pdf;

    @Override
    public void onEnable() {
        pdf = getDescription();

        // Startup logic (e.g., initialize data, register listeners)

        logInfo(String.format("[%s] v%s Enabled.", pdf.getName(), pdf.getVersion()));
    }

    @Override
    public void onDisable() {
        // Cleanup logic (e.g., save data, unregister listeners)

        logInfo(String.format("[%s] v%s Disabled.", pdf.getName(), pdf.getName()));
    }
}