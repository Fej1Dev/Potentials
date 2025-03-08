import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    java
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT"
}

architectury {
    val minecraft_version: String by project
    minecraft = minecraft_version
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")

    val minecraft_version: String by project
    val archives_name = rootProject.name

    val modLoader = project.layout.projectDirectory.asFile.name
    val isCommon = modLoader == "common"

    val isFabric = modLoader == "fabric"
    val isNeoForge = modLoader == "neoforge"

    base {
        archivesName.set("$archives_name-$modLoader-$minecraft_version")
    }

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()
        runs {
            named("client") {
                name("Test Client")
                source(sourceSets.test.get())
            }
            named("server") {
                name("Test Server")
                source(sourceSets.test.get())
            }
        }
    }

    repositories {
        maven (url = "https://maven.architectury.dev/")
        maven (url = "https://maven.minecraftforge.net/")
        maven (url = "https://maven.neoforged.net/releases/")
        maven (url = "https://maven.parchmentmc.org")
        maven (url = "https://prmaven.neoforged.net/NeoForge/pr794") {
            content {
                includeModule("net.neoforged", "neoforge")
                includeModule("net.neoforged", "testframework")
            }
        }
        exclusiveContent {
            forRepository {
                maven (url = "https://cursemaven.com")
            }
            filter {
                includeGroup("curse.maven")
            }
        }
        mavenLocal()
    }

    dependencies {

        "minecraft"("::$minecraft_version")

        @Suppress("UnstableApiUsage")
        "mappings"(project.the<LoomGradleExtensionAPI>().layered {
            val parchment_version: String by project

            officialMojangMappings()

            parchment(create(group = "org.parchmentmc.data", name = "parchment-1.21.1", version = parchment_version))
        })

        if (isFabric) {
            val fabric_loader_version: String by project
            val fabric_api_version: String by project

            "modImplementation"(group = "net.fabricmc", name = "fabric-loader", version = fabric_loader_version)
            "modApi"(group = "net.fabricmc.fabric-api", name = "fabric-api", version = fabric_api_version)
        }

        if (isNeoForge) {
            val neoforge_version: String by project
            "neoForge"(group = "net.neoforged", name = "neoforge", version = neoforge_version)
        }
    }

    java {
        withSourcesJar()
    }
}