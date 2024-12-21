package com.fej1fun.potentials.capabilities;

import com.fej1fun.potentials.capabilities.types.NoProviderBlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderFluidBlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderFluidItemCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.NoProviderItemCapabilityHolder;
import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import com.fej1fun.potentials.platform.CapabilitiesHelper;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public final class Capabilities {
    public static final class Energy {
        public static final NoProviderBlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> BLOCK = CapabilitiesHelper.getEnergyBlockCapability();
        public static final NoProviderItemCapabilityHolder<UniversalEnergyStorage, Void> ITEM = CapabilitiesHelper.getEnergyItemCapability();

        private Energy() {}
    }

    public static final class Fluid {
        public static final NoProviderFluidBlockCapabilityHolder<UniversalFluidStorage, @Nullable Direction> BLOCK = CapabilitiesHelper.getFluidBlockCapability();
        public static final NoProviderFluidItemCapabilityHolder<UniversalFluidStorage, Void> ITEM = CapabilitiesHelper.getFluidItemCapability();

        private Fluid() {}
    }

    private Capabilities() {}
}
