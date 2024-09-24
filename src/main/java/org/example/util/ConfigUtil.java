package org.example.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.example.util.LoggerUtil.logInfo;
import static org.example.util.LoggerUtil.logSevere;

/**
 * Utility class for managing plugin configuration files.
 * <p>
 * This class extends {@link Configuration} to provide custom methods for loading, saving, and managing
 * configuration files. It automatically handles the creation of parent directories and copies default configuration
 * files from the plugin's resources if they do not exist.
 * <p>
 * <b>Note:</b> This class allows for flexible management of multiple configuration files, specified by their file name.
 */
public class ConfigUtil extends Configuration {

    private final File configFile;
    private final String pluginName;

    /**
     * Constructs a new instance of {@code ConfigUtil}.
     *
     * @param plugin   the plugin instance using this configuration utility
     * @param fileName the name of the configuration file to manage (e.g., "config.yml", "settings.yml")
     */
    public ConfigUtil(JavaPlugin plugin, String fileName) {
        super(new File(plugin.getDataFolder(), fileName));
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.pluginName = plugin.getDescription().getName();
    }

    /**
     * Loads the configuration file.
     * <ul>
     * <li>Creates parent directories if they do not exist.</li>
     * <li>Copies the default configuration file from the plugin's resources if the configuration file does not exist.</li>
     * <li>Attempts to load the configuration by calling the superclass' {@code load()} method.</li>
     * <li>Logs errors if the configuration file cannot be loaded.</li>
     * </ul>
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
            logSevere(String.format("[%s] Failed to load config '%s': %s", pluginName, configFile.getName(), e.getMessage()));
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
            logSevere(String.format("[%s] Failed to create default config directory: %s", pluginName, e.getMessage()));
        }
    }

    /**
     * Copies the default configuration file from the plugin's resources to the target location.
     * <p>
     * This method looks for a file in the plugin's resources with the same name as the configuration file being managed.
     * If found, it copies this file to the plugin's data folder.
     * <p>
     * Logs an error if the default configuration file cannot be found or copied.
     */
    private void copyDefaultConfig() {
        // Adjust the path to ensure it's correct for your JAR structure
        String resourcePath = "/" + configFile.getName();

        try (InputStream input = getClass().getResourceAsStream(resourcePath)) {
            if (input == null) {
                logSevere(String.format("[%s] Default config '%s' wasn't found.", pluginName, configFile.getName()));
                return;
            }

            Files.copy(input, configFile.toPath());
            logInfo(String.format("[%s] Default config '%s' created successfully.", pluginName, configFile.getName()));
        } catch (IOException e) {
            logSevere(String.format("[%s] Failed to create default config '%s': %s", pluginName, configFile.getName(), e.getMessage()));
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
            logInfo(String.format("[%s] Config '%s' loaded successfully.", pluginName, configFile.getName()));
        } catch (Exception e) {
            logSevere(String.format("[%s] Failed to load config '%s': %s", pluginName, configFile.getName(), e.getMessage()));
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
            logInfo(String.format("[%s] Config '%s' saved successfully.", pluginName, configFile.getName()));
        } catch (Exception e) {
            logSevere(String.format("[%s] Failed to save config '%s': %s", pluginName, configFile.getName(), e.getMessage()));
        }
    }
}
