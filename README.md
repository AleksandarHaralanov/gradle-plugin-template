# Gradle Plugin Template
A gradle template to use as a base for Minecraft b1.7.3 plugin development.

## Prerequisites
- **JDK 8 Required**: This project is built upon JDK 8. Ensure that your development environment is set up with JDK 8 or a compatible version.

## Usage
While the project may be compatible with other Integrated Development Environments (IDEs), it is highly recommended to use IntelliJ IDEA by JetBrains for optimal performance.
1. Clone the repository by utilizing the Version Control System (VCS) and entering this GitHub repository's link when initiating a new project.
2. After cloning, update the `src/main/resources/plugin.yml` with your specific details.

## Features
1. [build.gradle](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/build.gradle) that names the plugin's compiled `.jar` file using the plugin's name and version directly from the `plugin.yml` file.
2. [Main](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/Main.java) class includes the following:
   - The `onEnable` and `onDisable` methods for the plugin's functionality.
   - Loggers that utilize the plugin's name and version for when the plugin is enabled and disabled.
3. The [storage](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/storage) package contains different APIs for you to utilize.
   - By default, the project is set up to use [Project Poseidon](https://github.com/RhysB/Project-Poseidon) as its API, but you can change it to something else if you prefer by using the storage. Check the `README.md` file inside for instructions on how to do that.

## Utility Classes
The [util](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util) package contains utility classes to assist with common tasks in plugin development. Here are the main utility classes included:

### [AboutUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/AboutUtil.java)
- **Purpose**: Displays detailed plugin information to the command sender, including the plugin's name, version, description, website, and authors.
- **Usage**: Can be used in commands to inform players or server administrators about the plugin details.
- **Features**:
   - Differentiates between players and console senders.
   - Warns when the plugin is running an experimental version (e.g., alpha, beta, snapshot).

### [ColorUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/ColorUtil.java)
- **Purpose**: Translates text by replacing `&` color codes with Minecraft's color codes (`§`).
- **Usage**: Commonly used to colorize in-game messages to players.

### [ConfigUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/ConfigUtil.java)
- **Purpose**: Manages the plugin's configuration files, handling loading, saving, and copying default configurations.
- **Usage**: Automatically copies the default `config.yml` from the plugin's resources to the plugin's directory on the server if it does not exist.
- **Features**:
   - Extends Bukkit's Configuration class.
   - Allows management of custom configuration files.

### [AccessUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/AccessUtil.java)
- **Purpose**: Manages access control for commands and checks user permissions.
- **Usage**: Ensures only players with the correct permissions can execute commands, and restricts certain commands to in-game use only.
- **Features**:
   - Custom permission checks.
   - Logs and informs users if they lack permission.

### [LoggerUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/LoggerUtil.java)
- **Purpose**: Provides simplified logging functionality, including logging to both the server console and a custom log file.
- **Usage**: Logs important plugin information, warnings, and errors. Also manages a log file within the plugin's data folder for more detailed logs.
- **Features**:
   - Supports logging of informational, warning, and severe messages.
   - Automatically creates log files if they do not exist.

### [UpdateUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/util/UpdateUtil.java)
- **Purpose**: Checks for plugin updates by querying the GitHub API for the latest release.
- **Usage**: Compares the current version with the latest version available on GitHub and informs server administrators if an update is available.
- **Features**:
   - Works with GitHub repositories.
   - Logs update information and provides a download link for the latest version.