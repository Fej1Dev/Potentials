import com.google.gson.Gson
import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    java
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("dev.architectury.loom") version "1.10-SNAPSHOT" apply false
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

architectury {
    val minecraft_version: String by project
    minecraft = minecraft_version
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "architectury-plugin")
    apply(plugin = "me.modmuss50.mod-publish-plugin")

    val minecraft_version: String by project
    val archives_name = rootProject.name
    val mod_version : String by project

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }


    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                groupId = "com.fej1fun.potentials"
                artifactId = "potentials-${modLoader}-${minecraft_version}"
                version = mod_version
                from(components["java"])
                pom {
                    name = "Potentials" + modLoader.capitalize()
                    url = "https://github.com/Fej1Dev/Potentials"
                }
            }
        }

        // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
        repositories {
            maven {
                name = "ExodusStudio"
                url = uri("https://maven.exodusstudio.org/releases")
                credentials {
                    username = System.getenv("MAVEN_USER")
                    password = System.getenv("MAVEN_PASS")
                }
            }
        }
    }

    if (!isCommon) {
        publishMods {

            val supported_minecraft_versions : String by project
            val parsedJson : Versions = Gson().fromJson(supported_minecraft_versions, Versions::class.java)
            val supportedVersions : Array<String> = parsedJson.versions
            val mod_version : String by project

            file = file("../${modLoader.capitalize()}/build/libs/potentials-$modLoader-$minecraft_version-$mod_version.jar")
            changelog = file("../changelog.md").readText(charset = charset("UTF-8"))

            val release_type : String by project
            if (release_type == "alpha")
                type = ALPHA
            else if (release_type == "beta")
                type = BETA
            else
                type = STABLE

            modLoaders.add(modLoader)
            displayName = "[${modLoader.capitalize()} ${minecraft_version}] Potentials ${mod_version}"

            curseforge {
                projectId = "1165185"
                projectSlug = "potentials" // Required for discord webhook
                accessToken = providers.gradleProperty("CURSEFORGE_API_KEY")
                for (version in supportedVersions)
                    minecraftVersions.add(version)

                javaVersions.add(JavaVersion.VERSION_21)
                requires("architectury-api")
                if (modLoader == "fabric") {
                    requires("fabric-api")
                }
            }

            modrinth {
                projectId = "J9pKOkxP"
                accessToken = providers.gradleProperty("MODRINTH_TOKEN")
                for (version in supportedVersions)
                    minecraftVersions.add(version)

                requires("architectury-api")
                if (modLoader == "fabric") {
                    requires("fabric-api")
                }

            }

//        github {
//            repository = "Fej1Dev/Potentials"
//            accessToken = providers.gradleProperty("GITHUB_TOKEN")
//            commitish = "main"
//            tagName = "${type}/${minecraft_version}"
//
//            // Upload the files to a previously created release, by providing another github publish task
//            // This is useful in multi-project builds where you want to publish multiple subprojects to a single release
//            parent project(":").tasks.named("publishGithub")
//
//            // Optionally allow the release to be created without any attached files.
//            // This is useful when you have subprojects using the parent option that you want to publish a single release.
//            allowEmptyFiles = true
//        }
        }
    }

}

data class Versions(val versions: Array<String>)