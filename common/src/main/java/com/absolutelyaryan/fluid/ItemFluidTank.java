package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class ItemFluidTank implements UniversalFluidTank {
    private final FluidStack stack;
    private long maxAmount;
    private final ItemStack itemStack;
    private final DataComponentType<Long> component;

    public ItemFluidTank(ItemStack itemStack, DataComponentType<Long> component, long maxAmount) {
        this.itemStack = itemStack;
        this.stack = FluidStack.empty();
        this.maxAmount = maxAmount;
        this.component = component;


        if(itemStack.has(this.component)) {
            try {
                this.stack.setAmount(itemStack.get(this.component));
            } catch (NullPointerException e){
                this.stack.setAmount(0);
            }

        }

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
        itemStack.set(this.component, amount);
        return amount;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        long amount = this.stack.getAmount() - stack.getAmount();
        if(amount < 0){
            amount = 0;
        }
        if(!simulate){
            stack.setAmount(0);
        }
        itemStack.set(this.component, amount);
        return amount;
    }

    @Override
    public void setFluidValue(long amount) {
        stack.setAmount(amount);
        itemStack.set(this.component, amount);
    }

    @Override
    public void setMaxAmount(long amount) {
        maxAmount = amount;
    }
}
