package com.fej1fun.potentials.platform.fabric;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.capabilities.holders.EnergyBlockHolder;
import com.fej1fun.potentials.fabric.capabilities.holders.EnergyItemHolder;
import com.fej1fun.potentials.fabric.capabilities.holders.FluidBlockHolder;
import com.fej1fun.potentials.fabric.capabilities.holders.FluidItemHolder;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
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

    public static NoProviderFluidBlockCapabilityHolder<UniversalFluidStorage, @Nullable Direction> getFluidBlockCapability() {
        return FluidBlockHolder.INSTANCE;
    }

    public static NoProviderFluidItemCapabilityHolder<UniversalFluidItemStorage, Void> getFluidItemCapability() {
        return FluidItemHolder.INSTANCE;
    }

}
