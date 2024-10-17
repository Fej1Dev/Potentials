package com.absolutelyaryan.neoforge;


import com.absolutelyaryan.BatteryItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber
public class NeoForgeEnergyHandler {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Register the energy capability for Forge compatibility

    }

    public static void registerBattery(BatteryItem battery) {

    }
}
