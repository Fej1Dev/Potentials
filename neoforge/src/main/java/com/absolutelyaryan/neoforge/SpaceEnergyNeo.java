package com.absolutelyaryan.neoforge;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.neoforge.capabilities.NeoForgeCapabilityManager;
import com.absolutelyaryan.neoforge.capabilities.types.NeoBlockHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoEntityHolder;
import com.absolutelyaryan.neoforge.capabilities.types.NeoItemHolder;
import com.absolutelyaryan.neoforge.capabilities.types.Registerable;
import com.absolutelyaryan.neoforge.energy.NeoForgeEnergyStorage;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidItem;
import com.absolutelyaryan.neoforge.fluid.NeoForgeFluidTank;
import com.absolutelyaryan.providers.EnergyProvider;
import com.absolutelyaryan.providers.FluidProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
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
