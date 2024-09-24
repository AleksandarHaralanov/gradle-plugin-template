package org.example.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.util.LoggerUtil.logInfo;
import static org.example.util.LoggerUtil.logWarning;

/**
 * Utility class for displaying plugin information to the command sender.
 * <p>
 * This class provides a method to display detailed information about a plugin, including its name, version,
 * description, website, and author(s). The information is formatted and sent to the command sender, which can be
 * either a player or the server console.
 */
public class AboutUtil {

    /**
     * Displays detailed information about the specified plugin to the command sender.
     * <p>
     * This method formats and sends plugin details such as the name, version, description, website, and authors
     * to the specified {@link CommandSender}. If the plugin version contains keywords like "snapshot", "alpha", "beta",
     * or "rc", a warning is displayed indicating that the plugin is experimental.
     *
     * @param sender the command sender who will receive the plugin information; can be a player or console
     * @param plugin the plugin for which information is displayed
     */
    public static void about(CommandSender sender, JavaPlugin plugin) {
        String name = plugin.getDescription().getName();
        String version = plugin.getDescription().getVersion();
        String website = plugin.getDescription().getWebsite();
        String description = plugin.getDescription().getDescription();
        String authors = formatAuthors(plugin.getDescription().getAuthors());

        boolean isExperimental = isExperimentalVersion(version);

        if (sender instanceof Player) {
            sendPlayerInfo((Player) sender, name, version, description, website, authors, isExperimental);
        } else {
            sendConsoleInfo(name, version, description, website, authors, isExperimental);
        }
    }

    /**
     * Formats the list of authors into a single string.
     * <p>
     * If the list contains multiple authors, they are joined by commas. Each author's name is prefixed with {@code &e}
     * for coloring in the player chat.
     *
     * @param authorsList the list of authors to format
     *
     * @return a formatted string of authors, or {@code null} if the list is empty or {@code null}
     */
    private static String formatAuthors(List<String> authorsList) {
        if (authorsList == null || authorsList.isEmpty()) {
            return null;
        }
        return authorsList.size() == 1 ? authorsList.get(0) : authorsList.stream()
                .map(author -> "&e" + author)
                .collect(Collectors.joining("&7, &e"));
    }

    /**
     * Determines if the plugin version is experimental based on its version string.
     * <p>
     * A version is considered experimental if it contains "snapshot", "alpha", "beta", or "rc".
     *
     * @param version the version string to check
     *
     * @return {@code true} if the version is experimental, otherwise {@code false}
     */
    private static boolean isExperimentalVersion(String version) {
        return (version.contains("snapshot") ||
                version.contains("alpha") ||
                version.contains("beta") ||
                version.contains("rc"));
    }

    /**
     * Sends the plugin information to a player.
     * <p>
     * This method sends formatted information including the plugin's name, version, description, website, and authors
     * to the specified player. If the plugin version is experimental, a warning message is also sent.
     *
     * @param player       the player to receive the plugin information
     * @param name         the name of the plugin
     * @param version      the version of the plugin
     * @param description  the description of the plugin, or {@code null} if not available
     * @param website      the website of the plugin, or {@code null} if not available
     * @param authors      the formatted string of authors, or {@code null} if not available
     * @param experimental {@code true} if the plugin version is experimental, otherwise {@code false}
     */
    private static void sendPlayerInfo(Player player, String name, String version, String description, String website, String authors, boolean experimental) {
        if (experimental) {
            player.sendMessage(ColorUtil.translate("&cRunning an experimental version."));
            player.sendMessage(ColorUtil.translate("&cMay contain bugs or other types of issues."));
        }
        player.sendMessage(ColorUtil.translate(String.format("&e%s &7version &e%s", name, version)));
        outputMessage(player, "&7", description);
        outputMessage(player, "&7Website: &e", website);
        outputMessage(player, "&7Author(s): &e", authors);
    }

    /**
     * Logs the plugin information to the server console.
     * <p>
     * This method logs formatted information including the plugin's name, version, description, website, and authors
     * to the server console. If the plugin version is experimental, a warning message is also logged.
     *
     * @param name         the name of the plugin
     * @param version      the version of the plugin
     * @param description  the description of the plugin, or {@code null} if not available
     * @param website      the website of the plugin, or {@code null} if not available
     * @param authors      the formatted string of authors, or {@code null} if not available
     * @param experimental {@code true} if the plugin version is experimental, otherwise {@code false}
     */
    private static void sendConsoleInfo(String name, String version, String description, String website, String authors, boolean experimental) {
        if (experimental) {
            logWarning("Running an experimental version.");
            logWarning("May contain bugs or other types of issues.");
        }
        logInfo(String.format("%s version %s", name, version));
        outputMessage(description);
        outputMessage("Website: ", website);
        outputMessage("Author(s): ", authors != null ? authors.replace("&e", "").replace("&7", "") : null);
    }

    /**
     * Sends a message to a player if the message is not {@code null}.
     * <p>
     * The message is prefixed with a specified string before being sent.
     *
     * @param player  the player to receive the message
     * @param prefix  the prefix to add to the message
     * @param message the message to send, or {@code null} if no message should be sent
     */
    private static void outputMessage(Player player, String prefix, String message) {
        if (message != null) {
            player.sendMessage(ColorUtil.translate(prefix + message));
        }
    }

    /**
     * Logs a message to the server console if the message is not {@code null}.
     *
     * @param message the message to log, or {@code null} if no message should be logged
     */
    private static void outputMessage(String message) {
        if (message != null) {
            logInfo(message);
        }
    }

    /**
     * Logs a message to the server console with a prefix if the message is not {@code null}.
     *
     * @param prefix  the prefix to add to the message
     * @param message the message to log, or {@code null} if no message should be logged
     */
    private static void outputMessage(String prefix, String message) {
        if (message != null) {
            logInfo(prefix + message);
        }
    }
}
