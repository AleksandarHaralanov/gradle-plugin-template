package org.example.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;
import static org.example.util.ColorUtil.translate;

/**
 * Utility class for handling access control and command restrictions.
 * <p>
 * This class provides methods to check permissions and enforce command usage restrictions based on the
 * sender's type (player or console).
 */
public class AccessUtil {

    private static final Logger logger = getServer().getLogger();

    /**
     * Checks if the sender has the specified permission.
     * <p>
     * If the sender is not a player (e.g., console), the method returns {@code true} by default.
     * If the sender does not have the required permission and is not an operator, a message is sent to the sender.
     *
     * @param sender     the entity executing the command, can be a player or console
     * @param permission the permission node to check
     * @param message    the message to send if the sender lacks the required permission
     * @return {@code true} if the sender has the permission or is an operator; {@code false} otherwise
     */
    public static boolean hasPermission(CommandSender sender, String permission, String message) {
        if (!(sender instanceof Player)) {
            return true;
        }

        boolean hasPermission = sender.hasPermission(permission);
        boolean isOp = sender.isOp();
        if (!(hasPermission || isOp)) {
            sender.sendMessage(translate(String.format("&c%s", message)));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the sender has the specified permission.
     * <p>
     * If the sender is not a player (e.g., console), the method returns {@code true} by default.
     * This method does not send any messages if the sender lacks permission.
     *
     * @param sender     the entity executing the command, can be a player or console
     * @param permission the permission node to check
     * @return {@code true} if the sender has the permission or is an operator; {@code false} otherwise
     */
    public static boolean hasPermission(CommandSender sender, String permission) {
        if (!(sender instanceof Player)) {
            return true;
        }

        boolean hasPermission = sender.hasPermission(permission);
        boolean isOp = sender.isOp();
        return hasPermission || isOp;
    }

    /**
     * Ensures that the command can only be executed in-game by a player.
     * <p>
     * If the sender is not a player (e.g., console), a message is logged to the console indicating that the command
     * must be executed in-game. This method is typically used to prevent console execution of player-only commands.
     *
     * @param sender the entity executing the command, typically the player or console
     * @return {@code true} if the sender is not a player, indicating that the command was blocked;
     *         {@code false} if the sender is a player, allowing the command to proceed
     */
    public static boolean commandInGameOnly(CommandSender sender) {
        if (!(sender instanceof Player)) {
            logger.info("You must be in-game to run this command.");
            return true;
        } else {
            return false;
        }
    }
}
