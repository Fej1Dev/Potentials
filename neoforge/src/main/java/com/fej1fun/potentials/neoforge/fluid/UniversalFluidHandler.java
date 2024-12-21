package com.fej1fun.potentials.neoforge.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class UniversalFluidHandler implements UniversalFluidStorage {
    private final IFluidHandler fluidHandler;

    public UniversalFluidHandler(IFluidHandler fluidHandler) {
        this.fluidHandler = fluidHandler;
    }

    @Override
    public int getTanks() {
        return fluidHandler.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return FluidStackHooksForge.fromForge(fluidHandler.getFluidInTank(tank));
    }

    @Override
    public long getTankCapacity(int tank) {
        return fluidHandler.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return fluidHandler.isFluidValid(tank, FluidStackHooksForge.toForge(stack));
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        return fluidHandler.fill(FluidStackHooksForge.toForge(stack), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        return FluidStackHooksForge.fromForge(fluidHandler.drain(FluidStackHooksForge.toForge(stack), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE));
    }

    @Override
    public FluidStack drain(int maxAmount, boolean simulate) {
        return FluidStackHooksForge.fromForge(fluidHandler.drain(maxAmount, simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE));
    }
}
