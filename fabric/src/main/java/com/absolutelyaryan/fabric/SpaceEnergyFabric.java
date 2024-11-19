package com.absolutelyaryan.fabric;

import com.absolutelyaryan.SpaceEnergyCommon;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.platform.CapabilitiesHelper;
import net.fabricmc.api.ModInitializer;
import com.absolutelyaryan.fabric.capabilities.FabricCapabilityManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public final class SpaceEnergyFabric implements ModInitializer {
    private final static FabricCapabilityManager capabilityManager = new FabricCapabilityManager();


    @Override
    public void onInitialize() {
        SpaceEnergyCommon.init();
        SpaceEnergyCommon.setCapabilityManager(capabilityManager);

        CapabilitiesHelper.ENERGY_LOOKUP = capabilityManager.registerSidedCapability(UniversalEnergyStorage.class, Direction.class, ResourceLocation.fromNamespaceAndPath("spaceenergy", "energy_storage"));
        CapabilitiesHelper.ENERGY_ITEM_LOOKUP = capabilityManager.registerItemCapability(UniversalEnergyStorage.class, Void.class, ResourceLocation.fromNamespaceAndPath("spaceenergy", "energy_storage_item"));
        CapabilitiesHelper.FLUID_LOOKUP = capabilityManager.registerSidedCapability(UniversalFluidTank.class, Direction.class, ResourceLocation.fromNamespaceAndPath("spaceenergy", "fluid_storage"));
        CapabilitiesHelper.FLUID_ITEM_LOOKUP = capabilityManager.registerItemCapability(UniversalFluidTank.class, Void.class, ResourceLocation.fromNamespaceAndPath("spaceenergy", "fluid_storage_item"));

    }
}
