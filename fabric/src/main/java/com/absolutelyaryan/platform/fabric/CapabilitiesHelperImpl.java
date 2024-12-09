package com.absolutelyaryan.platform.fabric;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fabric.capabilities.holders.EnergyBlockHolder;
import com.absolutelyaryan.fabric.capabilities.holders.EnergyItemHolder;
import com.absolutelyaryan.fabric.capabilities.holders.FluidBlockHolder;
import com.absolutelyaryan.fabric.capabilities.holders.FluidItemHolder;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class CapabilitiesHelperImpl {

    public static NoProviderBlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
        return EnergyBlockHolder.INSTANCE;
    }

    public static NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
        return EnergyItemHolder.INSTANCE;
    }

    public static NoProviderFluidBlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> getFluidBlockCapability() {
        return FluidBlockHolder.INSTANCE;
    }

    public static NoProviderFluidItemCapabilityHolder<UniversalFluidTank, Void> getFluidItemCapability() {
        return FluidItemHolder.INSTANCE;
    }

}
