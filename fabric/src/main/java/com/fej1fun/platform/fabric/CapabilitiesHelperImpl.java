package com.fej1fun.platform.fabric;

import com.fej1fun.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.energy.UniversalEnergyStorage;
import com.fej1fun.fabric.capabilities.holders.EnergyBlockHolder;
import com.fej1fun.fabric.capabilities.holders.EnergyItemHolder;
import com.fej1fun.fabric.capabilities.holders.FluidBlockHolder;
import com.fej1fun.fabric.capabilities.holders.FluidItemHolder;
import com.fej1fun.fluid.UniversalFluidTank;
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
