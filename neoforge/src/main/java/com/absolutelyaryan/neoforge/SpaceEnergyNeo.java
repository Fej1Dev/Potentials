package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.capabilities.NeoForgeCapabilityManager;
import com.absolutelyaryan.neoforge.capabilities.types.Registerable;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

@Mod(SpaceEnergyCommon.MOD_ID)
public class SpaceEnergyNeo {
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final NeoForgeCapabilityManager capabilityManager = new NeoForgeCapabilityManager();


    public SpaceEnergyNeo(IEventBus bus){
        bus.addListener(SpaceEnergyNeo::registerCapabilities);


        SpaceEnergyCommon.setCapabilityManager(capabilityManager);

        
    }


    //Using Neoforge's Event to register capabilities
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        Registerable.registerAll(event);
    }
}
