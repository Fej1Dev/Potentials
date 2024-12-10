package com.fej1fun.platform;

import com.fej1fun.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.energy.UniversalEnergyStorage;
import com.fej1fun.fluid.UniversalFluidTank;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class CapabilitiesHelper {
    @ExpectPlatform
    public static NoProviderBlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static NoProviderFluidBlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> getFluidBlockCapability() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static NoProviderFluidItemCapabilityHolder<UniversalFluidTank, Void> getFluidItemCapability() {
        throw new NotImplementedException();
    }
}
