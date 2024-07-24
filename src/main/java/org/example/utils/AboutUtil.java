package org.example.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public class AboutUtil {

    /**
     * Method for displaying plugin information to the command sender.
     * <p>
     * Display detailed information about a given plugin, such as its name, version, description, website, and author(s).
     * The information is formatted and sent to the command sender, which can be either a player or the server console.
     * </p>
     */
    public static void about(CommandSender sender, JavaPlugin plugin) {
        final String name = plugin.getDescription().getName();
        final String version = plugin.getDescription().getVersion();
        final String website = plugin.getDescription().getWebsite();
        final String description = plugin.getDescription().getDescription();
        final List<String> authorsList = plugin.getDescription().getAuthors();
        final String authors;

        if (authorsList.size() == 1) {
            authors = authorsList.get(0);
        } else {
            authors = authorsList.stream()
                    .map(author -> "&e" + author)
                    .collect(Collectors.joining("&7, &e"));
        }

        if (sender instanceof Player) {
            sender.sendMessage(ColorUtil.translate(String.format("&e%s &7version &e%s", name, version)));
            sender.sendMessage(ColorUtil.translate(String.format("&7%s", description)));
            sender.sendMessage(ColorUtil.translate(String.format("&7Website: &e%s", website)));
            sender.sendMessage(ColorUtil.translate(String.format("&7Author(s): %s", authors)));
        } else {
            getServer().getLogger().info(String.format("%s version %s", name, version));
            getServer().getLogger().info(String.format("%s", description));
            getServer().getLogger().info(String.format("Website: %s", website));
            getServer().getLogger().info(String.format("Author(s): %s", authors.replace("&e", "").replace("&7", "")));
        }
    }
}
