package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;

public class BaseFluidTank implements UniversalFluidTank {
    private FluidStack stack;
    private long maxAmount;

    public BaseFluidTank(long maxAmount) {
        this.stack = FluidStack.empty();
        this.maxAmount = maxAmount;
    }


    @Override
    public Fluid getBaseFluid() {
        return stack.getFluid();
    }

    @Override
    public long getFluidValue() {
        return stack.getAmount();
    }

    @Override
    public FluidStack getFluidStack() {
        return stack;
    }

    public void setFluidStack(FluidStack stack) {
        this.stack = stack;
    }

    @Override
    public long getMaxAmount() {
        return maxAmount;
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return this.stack.isFluidEqual(stack) || this.stack.isEmpty();
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        if (!isValid(stack)) return 0;

        long toReceive = Math.min(maxAmount, Math.min(stack.getAmount(), maxAmount - getFluidValue()));
        if (!simulate) {
            setFluidStack(FluidStack.create(stack, toReceive));
        }
        return toReceive;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        if (!isValid(stack)) return 0;

        long toDrain = Math.min(stack.getAmount(), getFluidValue());
        if (!simulate) {
            setFluidStack(FluidStack.create(this.stack, getFluidValue() - toDrain));
        }
        return toDrain;

    }

}
