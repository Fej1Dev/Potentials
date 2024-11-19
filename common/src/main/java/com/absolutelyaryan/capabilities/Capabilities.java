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

        public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getBlock() {
            return CapabilitiesHelper.getEnergyBlockCapability();
        }

        public static ItemCapabilityHolder<UniversalEnergyStorage, Void> getItem() {
            return CapabilitiesHelper.getEnergyItemCapability();
        }

        private Energy() {}
    }

    public static final class Fluid {

        public static BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> getBlock() {
            return CapabilitiesHelper.getFluidBlockCapability();
        }

        public static ItemCapabilityHolder<UniversalFluidTank, Void> getItem() {
            return CapabilitiesHelper.getFluidItemCapability();
        }




        private Fluid() {}
    }
}
