package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.world.level.material.Fluid;

public class BaseFluidTank implements UniversalFluidTank {
    private final FluidStack stack;
    private final long maxAmount;
    private long amount;

    public BaseFluidTank(long maxAmount, long amount) {
        this.stack = FluidStack.empty();
        this.maxAmount = maxAmount;
        this.amount = amount;
    }


    @Override
    public Fluid getBaseFluid() {
        return stack.getFluid();
    }

    @Override
    public long getFluidValue() {
        return amount;
    }

    @Override
    public FluidStack getFluidStack() {
        return stack;
    }

    @Override
    public long getMaxAmount() {
        return maxAmount;
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return this.stack.isFluidEqual(stack);
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        if(!simulate){
            amount = amount + stack.getAmount() > maxAmount ? amount + stack.getAmount() - maxAmount : stack.getAmount();
            this.stack.setAmount(amount);
            return amount;
        } else {
            return amount + stack.getAmount() > maxAmount ? amount + stack.getAmount() - maxAmount : stack.getAmount();
        }
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        if(!simulate){
            amount = amount - stack.getAmount() < 0 ? 0 : amount - stack.getAmount();
            this.stack.setAmount(amount);
            return amount;
        } else {
            return amount - stack.getAmount() < 0 ? 0 : amount - stack.getAmount();
        }
    }


}
