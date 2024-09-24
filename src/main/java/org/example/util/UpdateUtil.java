package org.example.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.example.util.LoggerUtil.*;

/**
 * Utility class for checking and comparing plugin versions with the latest release on GitHub.
 * <p>
 * This class queries the GitHub API for the latest release version and compares it with the current plugin version.
 * It logs messages indicating whether an update is available or if the plugin is up to date.
 */
public class UpdateUtil {

    /**
     * Checks for updates by querying a given GitHub API URL and comparing the current version with the latest
     * available version.
     * <p>
     * This method appends {@code v} to the front of the current version to comply with GitHub's semantic versioning
     * conventions. It then compares this formatted version with the latest release version obtained from the
     * GitHub API.
     * <p>
     * <b>Warning:</b> This method only works with GitHub repositories. Ensure that the GitHub API URL points to
     * the latest release information of your repository.
     *
     * @param plugin        the plugin instance using this update check utility
     * @param githubApiUrl  the GitHub API URL to query for the latest release information; should be in the format
     *                      {@code https://api.github.com/repos/USER/REPO/releases/latest}
     */
    public static void checkForUpdates(JavaPlugin plugin, String githubApiUrl) {
        PluginDescriptionFile pdf = plugin.getDescription();
        HttpURLConnection connection = null;
        try {
            URI uri = new URI(githubApiUrl);
            URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                handleResponseError(plugin.getDescription().getName(), responseCode);
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            String responseBody = content.toString();
            String latestVersion = getLatestVersion(responseBody);
            String formattedCurrentVersion = "v" + pdf.getVersion();
            compareVersions(pdf.getName(), formattedCurrentVersion, latestVersion, githubApiUrl);
        } catch (IOException | URISyntaxException e) {
            logSevere(String.format("[%s] Exception occurred while checking for a new version: %s", pdf.getName(), e.getMessage()));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Handles response errors when querying the GitHub API for the latest release version.
     * <p>
     * Logs appropriate warnings depending on the response code received.
     *
     * @param pluginName   the name of the plugin
     * @param responseCode the HTTP response code received from the GitHub API
     */
    private static void handleResponseError(String pluginName, int responseCode) {
        if (responseCode == 403 || responseCode == 429) {
            logWarning(String.format("[%s] Rate limited, can't check for a new plugin version. This should resolve itself within an hour.", pluginName));
        } else {
            logWarning(String.format("[%s] Unexpected response code: %s. Unable to check for a new plugin version.", pluginName, responseCode));
        }
    }

    /**
     * Extracts the latest version from the GitHub API response.
     * <p>
     * This method searches for the {@code tag_name} field in the JSON response and extracts the associated version
     * string.
     *
     * @param responseBody the JSON response from the GitHub API
     * @return the latest version string, or {@code null} if it cannot be determined
     */
    private static String getLatestVersion(String responseBody) {
        String tagNameField = "\"tag_name\":\"";
        int tagIndex = responseBody.indexOf(tagNameField);
        if (tagIndex == -1) {
            return null;
        }

        int startIndex = tagIndex + tagNameField.length();
        int endIndex = responseBody.indexOf("\"", startIndex);
        if (endIndex == -1) {
            return null;
        }

        return responseBody.substring(startIndex, endIndex);
    }

    /**
     * Compares the current plugin version with the latest version and logs the result.
     * <p>
     * If a newer version is available, this method logs a message indicating that the plugin is outdated and provides
     * a download link. If the plugin is up to date, it logs a message confirming this.
     *
     * @param pluginName    the name of the plugin
     * @param pluginVersion the current version of the plugin, formatted with a 'v' prefix
     * @param latestVersion the latest version retrieved from the GitHub API
     * @param githubApiUrl  the GitHub API URL used to check for the latest version
     */
    private static void compareVersions(String pluginName, String pluginVersion, String latestVersion, String githubApiUrl) {
        if (latestVersion == null) {
            logWarning(String.format("[%s] Could not determine the latest version.", pluginName));
            return;
        }

        if (!pluginVersion.equalsIgnoreCase(latestVersion)) {
            String downloadLink = githubApiUrl.replace("api.github.com/repos", "github.com");
            logInfo(String.format("[%s] New stable %s available, you are either running an outdated or experimental %s."
                    , pluginName, latestVersion, pluginVersion
            ));
            logInfo(String.format("[%s] Download the latest stable version from: %s", pluginName, downloadLink));
        } else {
            logInfo(String.format("[%s] You are running the latest version.", pluginName));
        }
    }
}
