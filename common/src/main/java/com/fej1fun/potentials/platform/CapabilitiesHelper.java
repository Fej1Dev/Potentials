package com.fej1fun.potentials.platform;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderEntityCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class CapabilitiesHelper {

    //Energy
    @ExpectPlatform
    public static NoProviderBlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
        throw new NotImplementedException();
    }
    @ExpectPlatform
    public static NoProviderEntityCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyEntityCapability() {
        throw new NotImplementedException();
    }
    @ExpectPlatform
    public static NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
        throw new NotImplementedException();
    }

    //Fluid
    @ExpectPlatform
    public static NoProviderBlockCapabilityHolder<UniversalFluidStorage, @Nullable Direction> getFluidBlockCapability() {
        throw new NotImplementedException();
    }
    @ExpectPlatform
    public static NoProviderEntityCapabilityHolder<UniversalFluidStorage, @Nullable Direction> getFluidEntityCapability() {
        throw new NotImplementedException();
    }
    @ExpectPlatform
    public static NoProviderFluidItemCapabilityHolder<UniversalFluidItemStorage, Void> getFluidItemCapability() {
        throw new NotImplementedException();
    }
}
