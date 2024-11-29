package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import net.fabricmc.api.ModInitializer;

public final class SpaceEnergyFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SpaceEnergyCommon.init();
    }
}
