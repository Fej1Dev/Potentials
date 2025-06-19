package com.fej1fun.potentials.platform.neoforge;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.neoforge.capabilities.holders.*;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class CapabilitiesHelperImpl {

    //Energy
    public static NoProviderBlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
        return EnergyBlockHolder.INSTANCE;
    }
    public static NoProviderEntityCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyEntityCapability() {
        return EnergyEntityHolder.INSTANCE;
    }
    public static NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
        return EnergyItemHolder.INSTANCE;
    }

    //Fluid
    public static NoProviderBlockCapabilityHolder<UniversalFluidStorage, @Nullable Direction> getFluidBlockCapability() {
        return FluidBlockHolder.INSTANCE;
    }
    public static NoProviderEntityCapabilityHolder<UniversalFluidStorage, @Nullable Direction> getFluidEntityCapability() {
        return FluidEntityHolder.INSTANCE;
    }
    public static NoProviderFluidItemCapabilityHolder<UniversalFluidItemStorage, Void> getFluidItemCapability() {
        return FluidItemHolder.INSTANCE;
    }

}
