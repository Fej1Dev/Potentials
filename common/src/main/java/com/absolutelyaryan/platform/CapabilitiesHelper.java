package com.absolutelyaryan.platform;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
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
    public static NoProviderItemCapabilityHolder<UniversalFluidTank, Void> getFluidItemCapability() {
        throw new NotImplementedException();
    }
}
