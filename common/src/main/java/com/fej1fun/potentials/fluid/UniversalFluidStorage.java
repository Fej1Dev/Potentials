package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;

public interface UniversalFluidStorage {
    int getTanks();
    FluidStack getFluidInTank(int tank);
    long getTankCapacity(int tank);
    boolean isFluidValid(int tank, FluidStack stack);
    long fill(FluidStack stack, boolean simulate);
    FluidStack drain(FluidStack stack, boolean simulate);
    FluidStack drain(int maxAmount, boolean simulate);
}
