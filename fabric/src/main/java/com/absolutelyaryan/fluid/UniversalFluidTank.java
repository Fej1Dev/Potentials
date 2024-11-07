package com.absolutelyaryan.fluid;

import net.minecraft.world.level.material.Fluid;

public interface UniversalFluidTank  {

    Fluid getBaseFluid();
    long getFluidValue();
    dev.architectury.fluid.FluidStack getFluidStack();
    long getMaxAmount();
    boolean isValid(dev.architectury.fluid.FluidStack stack);
    long fillFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);
    long drainFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);



}
