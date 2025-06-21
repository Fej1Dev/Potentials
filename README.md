# **Potentials**

----
***THIS MOD DOES NOTHING ON ITS OWN!***

Potentials allows mod makers to use Capabilities(Api lookups) for both
Neoforge and Fabric simultaneously!

You can create your own capabilities or use existing ones.
Current implementations:
- Energy Blocks and Block Entities
- Energy Entities
- Energy Items
- Fluid Storage Blocks and Block Entities
- Fluid Entities
- Fluid Storage Items

### How to Use?
Add the maven to your `build.gradle` file
```
repositories {
    maven { url "https://maven.exodusstudio.org/releases" }
}
```
Now add the api to each of your modules' `build.gradle` file
```
dependencies {
    modApi("com.fej1fun.potentials:potentials-${platform}-${mc_version}:${potentials_version}")
}
```
replace PLATFORM for that build.gradle's platform: common, fabric, neoforge.
replace VERSION for the latest Potentials version.

----
[![Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/potentials)
[![CurseForge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg)](https://legacy.curseforge.com/minecraft/mc-mods/potentials)

[![Architectury Api](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/requires/architectury-api_vector.svg)](https://www.curseforge.com/minecraft/mc-mods/architectury-api)
