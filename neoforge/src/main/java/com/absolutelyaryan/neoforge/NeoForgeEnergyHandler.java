package com.absolutelyaryan.neoforge;


import com.absolutelyaryan.items.BatteryItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber
public class NeoForgeEnergyHandler {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }

    public static void registerBattery(BatteryItem battery) {

    }
}
