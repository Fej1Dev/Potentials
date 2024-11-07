package com.absolutelyaryan.fluid;

import com.absolutelyaryan.items.DataComponents;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class ItemFluidTank implements UniversalFluidTank {
    private final FluidStack stack;
    private long maxAmount;
    private final ItemStack itemStack;

    public ItemFluidTank(ItemStack itemStack, long maxAmount) {
        this.itemStack = itemStack;
        this.stack = FluidStack.empty();
        this.maxAmount = maxAmount;


        if(itemStack.has(DataComponents.FLUID_VALUE_DATA_COMPONENT.get())) {
            try {
                this.stack.setAmount(itemStack.get(DataComponents.FLUID_VALUE_DATA_COMPONENT.get()));
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
        itemStack.set(DataComponents.FLUID_VALUE_DATA_COMPONENT.get(), amount);
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
        itemStack.set(DataComponents.FLUID_VALUE_DATA_COMPONENT.get(), amount);
        return amount;
    }

    @Override
    public void setFluidValue(long amount) {
        stack.setAmount(amount);
        itemStack.set(DataComponents.FLUID_VALUE_DATA_COMPONENT.get(), amount);
    }

    @Override
    public void setMaxAmount(long amount) {
        maxAmount = amount;
    }
}
