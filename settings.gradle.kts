enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven ( url = "https://maven.fabricmc.net/" )
        maven ( url = "https://maven.architectury.dev/" )
        maven ( url = "https://files.minecraftforge.net/maven/" )
        gradlePluginPortal()
    }
}

rootProject.name = "potentials"

include("common")
include("fabric")
include("neoforge")
//include 'test-common'
//include 'test-fabric'
//include 'test-neoforge'
