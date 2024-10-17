package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SpaceEnergyCommon.MOD_ID)
public class SpaceEnergyNeo {

    public SpaceEnergyNeo(IEventBus modBus){
        NeoForge.EVENT_BUS.addListener(SpaceEnergyNeo::registerCapabilities);
    }


    // Register capabilities here
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Register block capability
        event.registerBlock(EnergyCapabilities.BLOCK_ENERGY_CAPABILITY, (level, pos, state, blockEntity, side) -> {
           //TODO: return energyStorage for a block entity
            return null;
        });

        // Register item capability
        event.registerItem(EnergyCapabilities.ITEM_ENERGY_CAPABILITY, (stack, context) -> {
            // Provide the capability for the item stack
            return null;
            //TODO: return the correct IEnergyStorage
        });
    }
}
