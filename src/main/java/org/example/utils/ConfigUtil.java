package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import static org.bukkit.Bukkit.getServer;

public class ConfigUtil extends Configuration {

    private final File configFile;
    private final String pluginName;

    public ConfigUtil(File configFile, JavaPlugin plugin) {
        super(configFile);
        this.configFile = configFile;
        this.pluginName = plugin.getDescription().getName();
    }

    @Override
    public void load() {
        createParentDirectories();

        if (!configFile.exists()) {
            copyDefaultConfig();
        }

        try {
            super.load();
        } catch (Exception e) {
            getServer().getLogger().severe(String.format("[%s] Failed to load config: %s", pluginName, e.getMessage()));
        }
    }

    private void createParentDirectories() {
        try {
            Files.createDirectories(configFile.getParentFile().toPath());
        } catch (IOException e) {
            getServer().getLogger().severe(String.format("[%s] Failed to generate default config directory: %s", pluginName, e.getMessage()));
        }
    }

    private void copyDefaultConfig() {
        try (InputStream input = getClass().getResourceAsStream("/config.yml")) {
            if (input == null) {
                getServer().getLogger().severe(String.format("[%s] Default config wasn't found.", pluginName));
                return;
            }

            Files.copy(input, configFile.toPath());
            getServer().getLogger().info(String.format("[%s] Default config generated successfully.", pluginName));
        } catch (IOException e) {
            getServer().getLogger().severe(String.format("[%s] Failed to generate default config: %s", pluginName, e.getMessage()));
        }
    }

    public void loadConfig() {
        try {
            this.load();
            getServer().getLogger().info(String.format("[%s] Config loaded successfully.", pluginName));
        } catch (Exception e) {
            getServer().getLogger().severe(String.format("[%s] Failed to load config: %s", pluginName, e.getMessage()));
        }
    }

    public void saveConfig() {
        try {
            this.save();
            getServer().getLogger().info(String.format("[%s] Config saved successfully.", pluginName));
        } catch (Exception e) {
            getServer().getLogger().severe(String.format("[%s] Failed to save config: %s", pluginName, e.getMessage()));
        }
    }

    public File getConfig() {
        return configFile;
    }
}
