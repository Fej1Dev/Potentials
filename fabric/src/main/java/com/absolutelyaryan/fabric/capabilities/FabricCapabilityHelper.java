package com.absolutelyaryan.fabric.capabilities;


import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.fabric.capabilities.types.FabricBlockProviderHolder;
import com.absolutelyaryan.fluid.UniversalFluidTank;
import com.absolutelyaryan.platform.CapabilitiesHelper;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class FabricCapabilityHelper extends CapabilitiesHelper {


    public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {



        return null;
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
