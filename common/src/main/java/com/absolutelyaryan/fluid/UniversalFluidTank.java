package com.absolutelyaryan.fluid;


import dev.architectury.fluid.FluidStack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;

public interface UniversalFluidTank {

    Fluid getBaseFluid();
    long getFluidValue();
    dev.architectury.fluid.FluidStack getFluidStack();
    long getMaxAmount();
    boolean isValid(dev.architectury.fluid.FluidStack stack);
    long fillFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);
    long drainFluid(dev.architectury.fluid.FluidStack stack, boolean simulate);
    void setFluidValue(long amount);
    void setMaxAmount(long amount);
}
