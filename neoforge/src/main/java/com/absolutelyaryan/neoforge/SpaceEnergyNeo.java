package com.absolutelyaryan.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class SpaceEnergyNeo {


    // Register capabilities here
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Register block capability
        event.registerBlock(EnergyCapabilities.BLOCK_ENERGY_CAPABILITY, (level, pos, state, blockEntity, side) -> {
           // return energyStorage for a block entity
            return null;
        });

        // Register item capability
        event.registerItem(EnergyCapabilities.ITEM_ENERGY_CAPABILITY, (stack, context) -> {
            // Provide the capability for the item stack
            return null; // return the correct IEnergyStorage
        });
    }
}
