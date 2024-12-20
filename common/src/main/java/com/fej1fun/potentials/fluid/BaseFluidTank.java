package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;

@Deprecated
public class BaseFluidTank implements UniversalFluidTank {
    private FluidStack stack;
    private final long maxAmount;
    private final long maxFill;
    private final long maxDrain;

    public BaseFluidTank(long maxAmount, long maxFill, long maxDrain) {
        this.stack = FluidStack.empty();
        this.maxAmount = maxAmount;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
    }

    public BaseFluidTank(long maxAmount) {
        this(maxAmount, maxAmount, maxAmount);
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

        long toReceive = Math.clamp(this.maxAmount - getFluidValue(), 0, Math.min(this.maxFill, stack.getAmount()));
        if (!simulate) {
            setFluidStack(FluidStack.create(stack, getFluidValue() + toReceive));
        }
        return toReceive;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        if (!isValid(stack)) return 0;

        long toDrain = Math.min(getFluidValue(), Math.min(this.maxDrain, stack.getAmount()));
        if (!simulate) {
            setFluidStack(FluidStack.create(this.stack, getFluidValue() - toDrain));
        }
        return toDrain;

    }

}
