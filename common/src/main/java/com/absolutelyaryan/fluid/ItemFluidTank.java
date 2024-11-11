package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class ItemFluidTank implements UniversalFluidTank {
    private FluidStack stack;
    private long maxAmount;
    private final ItemStack itemStack;
    private final DataComponentType<FluidStack> component;

    public ItemFluidTank(ItemStack itemStack, DataComponentType<FluidStack> component, long maxAmount) {
        this.itemStack = itemStack;
        this.maxAmount = maxAmount;
        this.component = component;

        if (itemStack.has(this.component)) {
            stack = itemStack.get(this.component);
        } else {
            stack = FluidStack.empty();
            itemStack.set(this.component, stack);
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

    public void setFluidStack(FluidStack stack) {
        this.stack = stack;
    }

    @Override
    public long getMaxAmount() {
        return maxAmount;
    }

    @Override
    public boolean isValid(FluidStack otherStack) {
        return this.stack.isFluidEqual(otherStack);
    }

    @Override
    public long fillFluid(FluidStack fluidStack, boolean simulate) {
        if (this.stack.getFluid() == fluidStack.getFluid() || this.stack.isEmpty()) {
            long amount = this.stack.getAmount() + fluidStack.getAmount();
            if (amount > maxAmount) {
                amount = maxAmount;
            }

            if (!simulate) {
                setFluidStack(FluidStack.create(fluidStack, amount));
            }
            return amount;
        }
        return 0;
    }

    @Override
    public long drainFluid(FluidStack fluidStack, boolean simulate) {
        if (this.stack.getFluid() == fluidStack.getFluid() || this.stack.isEmpty()) {
            long amount = this.stack.getAmount() - fluidStack.getAmount();
            if (amount < 0) {
                amount = 0;
            }

            if (!simulate) {
                setFluidStack(FluidStack.create(fluidStack, amount));
            }
            return amount;
        }
        return 0;
    }

    @Override
    public void setFluidValue(long amount) {
        stack.setAmount(amount);
        itemStack.set(this.component, stack);
    }

    @Override
    public void setMaxAmount(long amount) {
        maxAmount = amount;
    }
}
