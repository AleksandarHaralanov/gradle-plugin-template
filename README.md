# Gradle Plugin Template
A gradle template to use as a base for [Poseidon](https://github.com/RhysB/Project-Poseidon), a fork of CB1060, for Minecraft b1.7.3 plugin development.

## Features
1. [build.gradle](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/build.gradle) that names the plugin's compiled `.jar` file using the plugin's name and version directly from the `plugin.yml` file.
2. [Main](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/Main.java) class includes the following:
   - The `onEnable` and `onDisable` methods for the plugin's functionality.
   - Loggers, that utilize the plugin's name and version, for when the plugin is enabled and disabled.
   - Method to get the plugin's instance for use in other classes.
3. My [`utils`](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utils) package with the following features:
   - [AboutUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utils/AboutUtil.java) - Can be executed, for example, within a command, to display your plugin's information, such as its name, version, description, website, and author(s).
   - [ColorUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utils/ColorUtil.java) - Translates given text using Minecraft color codes.
   - [ConfigUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utils/ConfigUtil.java) - Manages the plugin's configuration file, handling the loading, saving, and copying of default configurations. Extending the Bukkit Configuration class, it offers custom configuration management. Specifically, when you create a `config.yml` file inside the `resources` directory and compile the plugin, the plugin will copy this default configuration from the `.jar` file and create it inside the plugin's directory within the `plugins` folder if it doesn't already exist.
   - [UpdateUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utils/UpdateUtil.java) - Enables update checks by using the GitHub API to compare the plugin's current version specified in the `plugin.yml` file with the latest release tag of the plugin's GitHub repository.

While the project may be compatible with other Integrated Development Environments (IDEs), it is highly recommended to use IntelliJ IDEA by JetBrains for optimal performance.
1. Clone the repository by utilizing the Version Control System (VCS) and entering this GitHub repository's link when initiating a new project.
2. After cloning, update the `src/main/resources/plugin.yml`with your specific details.