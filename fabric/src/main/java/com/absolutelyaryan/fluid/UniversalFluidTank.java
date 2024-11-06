package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;

public interface UniversalFluidTank extends FabricFluidTank {

    Fluid getBaseFluid();
    long getFluidValue();
    dev.architectury.fluid.FluidStack getFluidStack();
    long getMaxAmount();
    boolean isValid(dev.architectury.fluid.FluidStack stack);
    long fillFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);
    long drainFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);

    default SingleVariantStorage<FluidVariant> getStorage(){
       return (SingleVariantStorage<FluidVariant>) this;
    }


    default SingleVariantStorage<FluidVariant> getVarStorage(){
        return getStorage();
    }


}
