# **Potentials**

----
***THIS MOD DOES NOTHING ON ITS OWN!***

Potentials allows mod makers to use capabilities(lookups) for both
Neoforge and Fabric simultaneously!

You can create your own capabilities or use existing ones.
Current implementations:
- Energy Blocks and Block Entities
- Energy Items
- Fluid Storage Blocks and Block Entities
- Fluid Storage Items

### How to Use?
Add the maven to your `build.gradle` file
```
repositories {
    maven { url "https://maven.odysseyus.fr/releases" }
}
```
Now add the api to each of your modules' `build.gradle` file
```
dependencies {
    modApi("com.fej1fun.potentials:potentials-PLATFORM:VERSION")
}
```
replace PLATFORM for that build.gradle's platform: common, fabric, neoforge.
replace VERSION for the latest Potentials version.

----
[![CurseForge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg)](https://legacy.curseforge.com/minecraft/mc-mods/potentials)
[![Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/potentials)

[![Architectury Api](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/requires/architectury-api_vector.svg)](https://www.curseforge.com/minecraft/mc-mods/architectury-api)
