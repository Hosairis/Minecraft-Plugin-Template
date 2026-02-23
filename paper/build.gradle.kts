plugins {
    alias(libs.plugins.bukkit.factory)      // Bukkit resource factory plugin for generating plugin.yml at build time
    alias(libs.plugins.run.paper)           // The run-task plugin for running a test server and testing the plugin
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)

    implementation(libs.bstats)
    implementation(libs.boosted.yaml)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21) // Use Java 17 toolchain
}

tasks {
    runServer {
        minecraftVersion("1.21.11")
        downloadPlugins {
            modrinth("viaversion", "5.7.2-SNAPSHOT+925")
            modrinth("viabackwards", "5.7.2-SNAPSHOT+548")
        }
    }

    shadowJar {
        relocate("org.bstats", "example.plugin.template.libs.bstats")
        relocate("dev.dejvokep.boostedyaml", "example.plugin.template.libs.boostedyaml")

        archiveFileName.set("${rootProject.name}-${project.version}.jar")
        minimize()
    }
}

bukkitPluginYaml {
    name = "MCTemplate"
    main = "example.plugin.template.Template"
    authors.add("Hosairis")
    apiVersion = "1.21"
}
