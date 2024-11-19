package com.absolutelyaryan.neoforge.capabilities;

import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.neoforge.capabilities.holders.EnergyBlockHolder;
import com.absolutelyaryan.platform.CapabilitiesHelper;
import net.minecraft.core.Direction;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class NeoForgeCapabilityHelper extends CapabilitiesHelper {

    public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
        return new EnergyBlockHolder<>();
    }


    public static ItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
        throw new NotImplementedException();
    }


    public static BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> getFluidBlockCapability() {
        throw new NotImplementedException();
    }


    public static ItemCapabilityHolder<UniversalFluidTank, Void> getFluidItemCapability() {
        throw new NotImplementedException();
    }

}
