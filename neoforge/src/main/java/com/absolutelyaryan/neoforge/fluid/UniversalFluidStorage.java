package com.absolutelyaryan.neoforge.fluid;

import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class UniversalFluidStorage implements UniversalFluidTank {
    private final IFluidHandler fluidHandler;

    public UniversalFluidStorage(IFluidHandler fluidHandler) {
        this.fluidHandler = fluidHandler;
    }

    @Override
    public Fluid getBaseFluid() {
        return fluidHandler.getFluidInTank(0).getFluid();
    }

    @Override
    public long getFluidValue() {
        return fluidHandler.getFluidInTank(0).getAmount();
    }

    @Override
    public FluidStack getFluidStack() {
        return FluidStackHooksForge.fromForge(fluidHandler.getFluidInTank(0));
    }

    @Override
    public long getMaxAmount() {
        return fluidHandler.getTankCapacity(0);
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return fluidHandler.isFluidValid(0, FluidStackHooksForge.toForge(stack));
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        return fluidHandler.fill(FluidStackHooksForge.toForge(stack), simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        return fluidHandler.drain(0, simulate ? IFluidHandler.FluidAction.SIMULATE : IFluidHandler.FluidAction.EXECUTE).getAmount();
    }

}
