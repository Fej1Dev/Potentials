package com.absolutelyaryan.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ItemFluidTank implements UniversalFluidTank {
    private long maxAmount;
    private final ItemStack itemStack;
    private final DataComponentType<FluidStack> component;

    public ItemFluidTank(ItemStack itemStack, DataComponentType<FluidStack> component, long maxAmount) {
        this.itemStack = itemStack;
        this.maxAmount = maxAmount;
        this.component = component;
    }

    @Override
    public Fluid getBaseFluid() {
        return itemStack.getOrDefault(component, FluidStack.empty()).getFluid();
    }

    @Override
    public long getFluidValue() {
        return itemStack.getOrDefault(component, FluidStack.empty()).getAmount();
    }

    @Override
    public FluidStack getFluidStack() {
        return itemStack.getOrDefault(component, FluidStack.empty());
    }

    public void setFluidStack(FluidStack stack) {
        itemStack.set(component, stack);
    }

    @Override
    public long getMaxAmount() {
        return maxAmount;
    }

    @Override
    public boolean isValid(FluidStack otherStack) {
        return getFluidStack().isFluidEqual(otherStack) || getFluidStack().isEmpty();
    }

    @Override
    public long fillFluid(FluidStack fluidStack, boolean simulate) {
        if (isValid(fluidStack)) {
            long amount = getFluidValue() + fluidStack.getAmount();
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
        if (isValid(fluidStack)) {
            long amount = getFluidValue() - fluidStack.getAmount();
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

}
