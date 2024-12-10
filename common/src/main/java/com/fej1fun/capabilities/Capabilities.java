package com.fej1fun.capabilities;

import com.fej1fun.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.energy.UniversalEnergyStorage;
import com.fej1fun.fluid.UniversalFluidTank;
import com.fej1fun.platform.CapabilitiesHelper;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public final class Capabilities {
    public static final class Energy {
        public static final NoProviderBlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> BLOCK = CapabilitiesHelper.getEnergyBlockCapability();
        public static final NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> ITEM = CapabilitiesHelper.getEnergyItemCapability();

        private Energy() {}
    }

    public static final class Fluid {
        public static final NoProviderFluidBlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> BLOCK = CapabilitiesHelper.getFluidBlockCapability();
        public static final NoProviderFluidItemCapabilityHolder<UniversalFluidTank, Void> ITEM = CapabilitiesHelper.getFluidItemCapability();

        private Fluid() {}
    }

    private Capabilities() {}
}
