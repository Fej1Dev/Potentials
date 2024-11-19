//package com.absolutelyaryan.fabric.capabilities;
//
//import com.absolutelyaryan.capabilities.types.BlockCapabilityHolder;
//import com.absolutelyaryan.capabilities.types.ItemCapabilityHolder;
//import com.absolutelyaryan.energy.UniversalEnergyStorage;
//import com.absolutelyaryan.fabric.capabilities.types.FabricBlockProviderHolder;
//import com.absolutelyaryan.fabric.capabilities.types.FabricItemProviderHolder;
//import com.absolutelyaryan.fluid.UniversalFluidTank;
//import com.absolutelyaryan.platform.CapabilitiesHelper;
//import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
//import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
//import net.minecraft.core.Direction;
//import net.minecraft.resources.ResourceLocation;
//import org.jetbrains.annotations.Nullable;
//
//public class FabricCapabilityHelper extends CapabilitiesHelper {
//
//    public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> ENERGY_LOOKUP;
//
//    public static BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> FLUID_LOOKUP;
//
//    public static ItemCapabilityHolder<UniversalEnergyStorage, Void> ENERGY_ITEM_LOOKUP;
//    public static ItemCapabilityHolder<UniversalFluidTank, Void> FLUID_ITEM_LOOKUP;
//
//
//    public static BlockCapabilityHolder<UniversalEnergyStorage, @Nullable Direction> getEnergyBlockCapability() {
//        return ENERGY_LOOKUP;
//    }
//
//    public static ItemCapabilityHolder<UniversalEnergyStorage, Void> getEnergyItemCapability() {
//        return ENERGY_ITEM_LOOKUP;
//    }
//
//    public static BlockCapabilityHolder<UniversalFluidTank, @Nullable Direction> getFluidBlockCapability() {
//       return FLUID_LOOKUP;
//    }
//
//    public static ItemCapabilityHolder<UniversalFluidTank, Void> getFluidItemCapability() {
//        return FLUID_ITEM_LOOKUP;
//    }
//
//
//
//
//
//
//}
