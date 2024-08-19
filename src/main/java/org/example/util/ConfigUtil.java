package org.example.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.bukkit.Bukkit.getServer;

/**
 * Utility class for managing plugin configuration files.
 * <p>
 * This class extends {@link Configuration} to provide custom methods for loading, saving, and managing
 * configuration files. It automatically handles the creation of parent directories and copies default configuration
 * files if they do not exist.
 * <p>
 * <b>Note:</b> This class assumes the presence of a pre-made {@code config.yml} file in the plugin's resources.
 */
public class ConfigUtil extends Configuration {

    private final File configFile;
    private final String pluginName;

    /**
     * Constructs a new instance of {@code ConfigUtil}.
     *
     * @param configFile the configuration file to manage
     * @param plugin     the plugin instance using this configuration utility
     */
    public ConfigUtil(File configFile, JavaPlugin plugin) {
        super(configFile);
        this.configFile = configFile;
        this.pluginName = plugin.getDescription().getName();
    }

    /**
     * Loads the configuration file.
     * <ul>
     * <li>Creates parent directories if they do not exist.</li>
     * <li>Copies the default configuration file from resources if the configuration file does not exist.</li>
     * <li>Attempts to load the configuration by calling the superclass' {@code load()} method.</li>
     * <li>Logs errors if the configuration file cannot be loaded.</li>
     */
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

    /**
     * Creates the parent directories for the configuration file if they do not exist.
     * <p>
     * Logs an error if the directories cannot be created.
     */
    private void createParentDirectories() {
        try {
            Files.createDirectories(configFile.getParentFile().toPath());
        } catch (IOException e) {
            getServer().getLogger().severe(String.format("[%s] Failed to generate default config directory: %s", pluginName, e.getMessage()));
        }
    }

    /**
     * Copies the default configuration file from the plugin's resources to the target location.
     * <p>
     * Logs an error if the default configuration file cannot be found or copied.
     */
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

    /**
     * Loads the configuration file and logs the result.
     * <p>
     * Logs a message indicating whether the configuration was loaded successfully.
     */
    public void loadConfig() {
        try {
            this.load();
            getServer().getLogger().info(String.format("[%s] Config loaded successfully.", pluginName));
        } catch (Exception e) {
            getServer().getLogger().severe(String.format("[%s] Failed to load config: %s", pluginName, e.getMessage()));
        }
    }

    /**
     * Saves the configuration file and logs the result.
     * <p>
     * Logs a message indicating whether the configuration was saved successfully.
     */
    public void saveConfig() {
        try {
            this.save();
            getServer().getLogger().info(String.format("[%s] Config saved successfully.", pluginName));
        } catch (Exception e) {
            getServer().getLogger().severe(String.format("[%s] Failed to save config: %s", pluginName, e.getMessage()));
        }
    }

    /**
     * Returns the configuration file managed by this utility.
     *
     * @return the configuration file
     */
    public File getConfig() {
        return configFile;
    }
}