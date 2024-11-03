package com.absolutelyaryan.energy;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public interface UniversalFluidTank extends IFluidTank  {


    int getFluidValue();
    Object getFluidObject();
    int getMaxAmount();
    boolean isValid();
    int fillFluid(int amount);
    int drainFluid(int amount);


    @Override
    FluidStack getFluid();
    @Override
    int getFluidAmount();
    @Override
    int getCapacity();
    @Override
    boolean isFluidValid(FluidStack fluidStack);
    @Override
    int fill(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction);
    @Override
    FluidStack drain(int i, IFluidHandler.FluidAction fluidAction);
    @Override
    FluidStack drain(FluidStack fluidStack, IFluidHandler.FluidAction fluidAction);
}
