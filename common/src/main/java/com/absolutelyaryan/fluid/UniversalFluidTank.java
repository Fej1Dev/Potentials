package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;

public interface UniversalFluidTank {

    Fluid getBaseFluid();
    long getFluidValue();
    FluidStack getFluidStack();
    long getMaxAmount();
    boolean isValid(FluidStack stack);
    long fillFluid(FluidStack stack, boolean simulate);
    long drainFluid(FluidStack stack, boolean simulate);

}
