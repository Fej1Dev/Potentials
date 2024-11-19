package com.absolutelyaryan.capabilities;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.platform.CapabilitiesHelper;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class Capabilities {
    public static final class Energy {
        public static final BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> BLOCK = CapabilitiesHelper.getEnergyBlockCapability();
        public static final ItemCapabilityHolder<UniversalEnergyStorage, Void> ITEM = CapabilitiesHelper.getEnergyItemCapability();

        private Energy() {}
    }

    public static final class Fluid {
        public static final BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> BLOCK = CapabilitiesHelper.getFluidBlockCapability();
        public static final ItemCapabilityHolder<UniversalFluidTank, Void> ITEM = CapabilitiesHelper.getFluidItemCapability();

        private Fluid() {}
    }
}
