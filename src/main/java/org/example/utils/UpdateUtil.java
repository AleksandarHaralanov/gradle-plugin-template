package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.bukkit.Bukkit.getServer;

public class UpdateUtil {

    /**
     * Checks for updates by querying a given GitHub API URL and comparing the current version with the latest available version.
     * <p>
     * This method appends 'v' to the front of the current version to comply with GitHub's semantic versioning conventions.
     * If your plugin's release tag does not include 'v' in front of the version number, this method will not recognize it as an update.
     * </p>
     * <p><b>Caution:</b> This method only works with GitHub repositories. You will need to modify it if you use another platform.</p>
     * <p><b>Warning:</b> Ensure that the GitHub API URL points to the latest release information of your repository.</p>
     *
     * @param pluginName     The name of the plugin.
     * @param pluginVersion  The current version of the plugin.
     * @param githubApiUrl   The GitHub API URL to query for the latest release information.
     *                       <p>Example for <b>githubApiUrl</b>: {@code https://api.github.com/repos/USER/REPO/releases/latest}</p>
     */
    public static void checkForUpdates(final String pluginName, final String pluginVersion, final String githubApiUrl) {
        HttpURLConnection connection = null;
        try {
            final URI uri = new URI(githubApiUrl);
            final URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            final int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                if (responseCode == 403 || responseCode == 429) {
                    getServer().getLogger().warning(String.format("[%s] Rate limited, can't check for a new plugin version. This should resolve itself within an hour.", pluginName));
                } else {
                    getServer().getLogger().warning(String.format("[%s] Unexpected response code: %s. Unable to check for a new plugin version.", pluginName, responseCode));
                }
                return;
            }

            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            final String responseBody = content.toString();
            final String latestVersion = getLatestVersion(responseBody);
            final String formattedCurrentVersion = "v" + pluginVersion;
            compareVersions(pluginName, formattedCurrentVersion, latestVersion, githubApiUrl);
        } catch (final IOException | URISyntaxException e) {
            getServer().getLogger().severe(String.format("[%s] Exception occurred while checking for a new version: %s", pluginName, e.getMessage()));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String getLatestVersion(final String responseBody) {
        final String tagNameField = "\"tag_name\":\"";
        final int tagIndex = responseBody.indexOf(tagNameField);
        if (tagIndex == -1) {
            return null;
        }

        final int startIndex = tagIndex + tagNameField.length();
        final int endIndex = responseBody.indexOf("\"", startIndex);
        if (endIndex == -1) {
            return null;
        }

        return responseBody.substring(startIndex, endIndex);
    }

    private static void compareVersions(final String pluginName, final String pluginVersion, final String latestVersion, final String githubApiUrl) {
        if (latestVersion == null) {
            getServer().getLogger().warning(String.format("[%s] Could not determine the latest version.", pluginName));
            return;
        }

        if (!pluginVersion.equalsIgnoreCase(latestVersion)) {
            final String downloadLink = githubApiUrl.replace("api.github.com/repos", "github.com");
            getServer().getLogger().info(String.format("[%s] New %s available, you are running an OUTDATED %s!", pluginName, latestVersion, pluginVersion));
            getServer().getLogger().info(String.format("[%s] Download the latest version from: %s", pluginName, downloadLink));
        } else {
            getServer().getLogger().info(String.format("[%s] You are running the latest version.", pluginName));
        }
    }
}