# Gradle Plugin Template
A gradle template to use as a base for Minecraft b1.7.3 plugin development.

## Usage
While the project may be compatible with other Integrated Development Environments (IDEs), it is highly recommended to use IntelliJ IDEA by JetBrains for optimal performance.

### How To Clone
<details>
    <summary><b>Via VCS</b></summary>

1. Open IntelliJ IDEA.
2. Go to `File > New > Project from Version Control`.
   - If you're on the dashboard home screen of the IDE, click `CLONE FROM VCS` on the top-right button.
3. In the `URL` field, paste the repository link:
```
https://github.com/AleksandarHaralanov/gradle-plugin-template
```
4. Rename your project to have your desired plugin name.
5. Click `Clone` to download the project.

</details>

<details>
    <summary><b>Via Git</b></summary>

1. Open your terminal and run the following command:
```bash
git clone https://github.com/AleksandarHaralanov/gradle-plugin-template.git <project-name>
```

</details>

### Notice
After cloning, update the `src/main/resources/plugin.yml` with your specific details.

## Prerequisites
- **JDK 8 Required**: This project is built upon JDK 8. Ensure that your development environment is set up with JDK 8 or a compatible version.

## Features
### Convenience
- [build.gradle](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/build.gradle) that names the plugin's compiled `.jar` file using the plugin's name and version directly from the `plugin.yml` file.
- [Main](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/Main.java) class includes the following:
   - The `onEnable` and `onDisable` methods for the plugin's functionality.
   - Loggers that utilize the plugin's name and version for when the plugin is enabled and disabled.
- The [api](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/api) package contains different APIs for you to utilize.

### Utility Classes
The [util](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util) package contains utility classes to assist with common tasks in plugin development. They come with well-structured JavaDocs to facilitate ease of use and understanding. Below is an overview of the main utility classes included:

#### [AboutUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/AboutUtil.java)
- **Purpose**: Displays detailed plugin information to the command sender, including the plugin's name, version, description, website, and authors.
- **Usage**: Typically used in commands to inform players or server administrators about plugin details.
- **Features**:
  - Detects whether the sender is a player or console and adjusts the output accordingly.
  - Warns if the plugin is running an experimental version (e.g., snapshot, alpha, beta, rc).

#### [AccessUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/AccessUtil.java)
- **Purpose**: Handles access control for commands by checking permissions and restricting execution based on sender type (player or console).
- **Usage**: Ensures only players with the correct permissions can execute commands, and restricts certain commands to in-game use only (i.e., players rather than the console).
- **Features**:
  - Custom permission checks to manage access control.
  - Logs appropriate messages when a user lacks permission or attempts to execute a restricted command.

#### [ColorUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/ColorUtil.java)
- **Purpose**: Translates text by replacing `&` color codes with Minecraft’s color codes (`§`), allowing for colorized messages in-game.
- **Usage**: Ideal for formatting and colorizing text that appears in chat or other in-game messages to players.
- **Features**:
  - Automatically converts valid color codes (`0-9, a-f, A-F`) to Minecraft's recognized format.
  - Ensures that color codes are correctly formatted for both players and console output.

#### [ConfigUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/ConfigUtil.java)
- **Purpose**: Manages configuration files, allowing for loading, saving, and automatic copying of default configurations from the plugin's resources.
- **Usage**: Automatically handles the creation of configuration files, such as `config.yml`, in the plugin's directory if they do not exist.
- **Features**:
  - Extends Bukkit’s Configuration class, providing custom methods for managing configuration files.
  - Ensures parent directories are created if needed and copies default configurations from resources to the plugin’s directory.

#### [LoggerUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/LoggerUtil.java)
- **Purpose**: Simplifies logging of information to both the server console and a custom log file.
- **Usage**: Used to log important plugin information, warnings, and errors. Additionally, it manages a custom log file for more detailed logging.
- **Features**:
  - Supports logging of informational, warning, and severe messages to the server console.
  - Automatically initializes log files within the plugin’s data folder, ensuring they are created if missing.

#### [UpdateUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/UpdateUtil.java)
- **Purpose**: Checks for plugin updates by querying the GitHub API for the latest release version and comparing it with the current version.
- **Usage**: Can be used to notify server administrators when a new version of the plugin is available for download.
- **Features**:
  - Works with GitHub repositories to fetch the latest release information.
  - Compares the current version with the latest available version and logs a message if an update is available, along with a download link.
