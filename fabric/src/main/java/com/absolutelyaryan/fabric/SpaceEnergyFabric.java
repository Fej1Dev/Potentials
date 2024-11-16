package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import net.fabricmc.api.ModInitializer;
import com.absolutelyaryan.fabric.capabilities.FabricCapabilityManager;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;

public final class SpaceEnergyFabric implements ModInitializer {
    private final static FabricCapabilityManager capabilityManager = new FabricCapabilityManager();

    @Override
    public void onInitialize() {
        SpaceEnergyCommon.init();
        SpaceEnergyCommon.setCapabilityManager(capabilityManager);



    }
}
