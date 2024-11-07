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
        return this.stack.isFluidEqual(stack);
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        long amount = this.stack.getAmount() + stack.getAmount();
        if(amount > maxAmount){
            amount = maxAmount;
        }

        if(!simulate){
            this.stack.setAmount(amount);
        }
        return amount;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        long amount = this.stack.getAmount() - stack.getAmount();
        if(amount < 0){
            amount = 0;
        }
        if(!simulate){
           stack.setAmount(amount);
        }
        return amount;
    }

    @Override
    public void setFluidValue(long amount) {
        stack.setAmount(amount);
    }

    @Override
    public void setMaxAmount(long amount) {
        maxAmount = amount;
    }


}
