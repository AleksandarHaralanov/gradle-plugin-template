package org.example.util;

import static org.bukkit.Bukkit.getServer;

/**
 * Utility class for logging messages to the server console.
 * <p>
 * This class provides static methods for logging informational, warning, and severe messages
 * to the server's logger, simplifying the process of logging by avoiding the need to directly
 * access the logger and call these methods individually each time.
 * <p>
 * By using this utility, you can log messages with a single method call, making your code cleaner
 * and easier to maintain.
 */
public class LoggerUtil {

    /**
     * Logs an informational message to the server console.
     *
     * @param message the message to log
     */
    public static void logInfo(String message) {
        getServer().getLogger().info(message);
    }

    /**
     * Logs a warning message to the server console.
     *
     * @param message the message to log
     */
    public static void logWarning(String message) {
        getServer().getLogger().warning(message);
    }

    /**
     * Logs a severe message to the server console.
     *
     * @param message the message to log
     */
    public static void logSevere(String message) {
        getServer().getLogger().severe(message);
    }
}