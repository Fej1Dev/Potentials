architectury {
    val enabled_platforms: String by rootProject
    common(enabled_platforms.split(","))
}

dependencies {
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    val fabric_loader_version: String by project
    val architectury_api_version: String by project

    modImplementation("net.fabricmc:fabric-loader:$fabric_loader_version")

    // Architectury API. This is optional, and you can comment it out if you don't need it.
    modImplementation("dev.architectury:architectury:$architectury_api_version")
}