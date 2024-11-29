package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.capabilities.Registerable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod(SpaceEnergyCommon.MOD_ID)
public class SpaceEnergyNeo {

    public SpaceEnergyNeo(IEventBus bus){
        SpaceEnergyCommon.init();
        bus.addListener(SpaceEnergyNeo::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        Registerable.registerAll(event);
    }
}
