# Gradle Plugin Template
A gradle template to use as a base for [Poseidon](https://github.com/RhysB/Project-Poseidon), a fork of CB1060, for Minecraft b1.7.3 plugin development.

## Features
1. [build.gradle](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/build.gradle) that names the plugin's compiled `.jar` file using the plugin's name and version directly from the [`plugin.yml`](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/resources/plugin.yml) file.
2. [Main](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/Main.java) class includes the following:
   - The `onEnable` and `onDisable` methods for the plugin's functionality.
   - Loggers, that utilize the plugin's name and version, for when the plugin is enabled and disabled.
   - Method to get the plugin's instance for use in other classes.
3. My [`utilities`](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utilities) package with the following features:
    - [UpdateUtil](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/java/org/example/utilities/UpdateUtil.java) - Enables update checks by using the GitHub API to compare the plugin's current version specified in the [`plugin.yml`](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/resources/plugin.yml) file with the latest release tag of the plugin's GitHub repository.

## Usage
While the project may be compatible with other Integrated Development Environments (IDEs), it is highly recommended to use IntelliJ IDEA by JetBrains for optimal performance.
1. Clone the repository by utilizing the Version Control System (VCS) and entering this GitHub repository's link when initiating a new project.
2. After cloning, update the [`src/main/resources/plugin.yml`](https://github.com/AleksandarHaralanov/Gradle-Plugin-Template/blob/master/src/main/resources/plugin.yml) with your specific details.