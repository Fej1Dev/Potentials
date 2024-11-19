package com.absolutelyaryan.platform;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class CapabilitiesHelper {

    public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> ENERGY_LOOKUP;
    public static BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> FLUID_LOOKUP;
    public static ItemCapabilityHolder<UniversalEnergyStorage, Void> ENERGY_ITEM_LOOKUP;
    public static ItemCapabilityHolder<UniversalFluidTank, Void> FLUID_ITEM_LOOKUP;

    public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
        return ENERGY_LOOKUP;
    }

    public static ItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
        return ENERGY_ITEM_LOOKUP;
    }

    public static BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> getFluidBlockCapability() {
        return FLUID_LOOKUP;
    }

    public static ItemCapabilityHolder<UniversalFluidTank, Void> getFluidItemCapability() {
        return FLUID_ITEM_LOOKUP;
    }
}
