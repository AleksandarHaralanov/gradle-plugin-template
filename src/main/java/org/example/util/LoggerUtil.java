package org.example.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

/**
 * Utility class for logging messages to the server console and managing a log file.
 * <p>
 * This class provides static methods for logging informational, warning, and severe messages
 * to the server's logger, simplifying the process of logging by avoiding the need to directly
 * access the logger. Additionally, it manages a log file within the plugin's data folder where
 * custom log messages can be written.
 * <p>
 * By using this utility, you can log messages with a single method call, making your code cleaner
 * and easier to maintain. You can also initialize and manage a log file for additional logging purposes.
 */
public class LoggerUtil extends Configuration {

    private static File logFile;
    private static String pluginName;
    private static final Logger logger = getServer().getLogger();

    /**
     * Constructs a LoggerUtil instance.
     * <p>
     * This constructor initializes the LoggerUtil with the plugin's data folder and a specified
     * log file name. It also retrieves the plugin's name from its description for use in log messages.
     *
     * @param plugin   the plugin instance, used to access its data folder and description
     * @param fileName the name of the log file to be used for logging messages
     */
    public LoggerUtil(JavaPlugin plugin, String fileName) {
        super(new File(plugin.getDataFolder(), fileName));
        logFile = new File(plugin.getDataFolder(), fileName);
        pluginName = plugin.getDescription().getName();
    }

    /**
     * Initializes the log file.
     * <p>
     * This method checks if the log file exists in the plugin's data folder. If it does not exist,
     * it attempts to create the file. If the file is successfully created, an informational log message
     * is generated. If the file creation fails, a severe log message is logged.
     */
    public void initializeLog() {
        if (!logFile.exists()) {
            try {
                if (logFile.createNewFile()) {
                    logger.info(String.format("[%s] Log '%s' created successfully.", pluginName, logFile.getName()));
                }
            } catch (IOException e) {
                logger.severe(String.format("[%s] Could not create log '%s': %s", pluginName, logFile.getName(), e.getMessage()));
            }
        }
    }

    /**
     * Writes text to the log file with an optional timestamp and a newline at the end.
     * <p>
     * This method appends the provided text to the log file, optionally prepending the current date and time.
     * If the log file does not exist, ensure it has been initialized using {@link #initializeLog()} before calling this method.
     *
     * @param text        the text to write to the log file
     * @param logDateTime if {@code true}, prepends the current date and time to the log entry
     */
    public static void writeToLog(String text, boolean logDateTime) {
        String logEntry;
        if (logDateTime) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timeStamp = now.format(formatter);

            logEntry = String.format("[%s] %s", timeStamp, text);
        } else {
            logEntry = text;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            logger.severe(String.format("[%s] Could not write to log '%s': %s", pluginName, logFile.getName(), e.getMessage()));
        }
    }

    /**
     * Logs an informational message to the server console.
     * <p>
     * Use this method to log general information that can help with understanding server or plugin behavior.
     *
     * @param message the message to log
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Logs a warning message to the server console.
     * <p>
     * Use this method to log warnings that indicate potential issues or problems that may not cause immediate failures
     * but should be addressed.
     *
     * @param message the message to log
     */
    public static void logWarning(String message) {
        logger.warning(message);
    }

    /**
     * Logs a severe message to the server console.
     * <p>
     * Use this method to log critical issues that may prevent the server or plugin from functioning correctly.
     *
     * @param message the message to log
     */
    public static void logSevere(String message) {
        logger.severe(message);
    }
}
