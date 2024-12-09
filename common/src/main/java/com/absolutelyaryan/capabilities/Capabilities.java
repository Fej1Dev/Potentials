package com.absolutelyaryan.capabilities;

import com.absolutelyaryan.capabilities.types.NoProviderBlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.absolutelyaryan.capabilities.types.NoProviderItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.platform.CapabilitiesHelper;
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
