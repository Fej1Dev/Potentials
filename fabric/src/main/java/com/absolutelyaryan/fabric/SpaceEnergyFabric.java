package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.fabric.capabilities.FabricCapabilityManager;
import net.fabricmc.api.ModInitializer;

public final class SpaceEnergyFabric implements ModInitializer {
    private final static FabricCapabilityManager capabilityManager = new FabricCapabilityManager();

    @Override
    public void onInitialize() {
        SpaceEnergyCommon.init();
        SpaceEnergyCommon.setCapabilityManager(capabilityManager);
    }
}
