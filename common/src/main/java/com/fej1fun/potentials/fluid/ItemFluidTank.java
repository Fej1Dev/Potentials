package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

@Deprecated
public class ItemFluidTank implements UniversalFluidTank {
    private final long maxAmount;
    private final long maxFill;
    private final long maxDrain;
    private final ItemStack itemStack;
    private final DataComponentType<FluidStack> component;

    public ItemFluidTank(ItemStack itemStack, DataComponentType<FluidStack> component, long maxAmount,  long maxFill, long maxDrain) {
        this.itemStack = itemStack;
        this.maxAmount = maxAmount;
        this.component = component;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
    }

    public ItemFluidTank(ItemStack itemStack, DataComponentType<FluidStack> component, long maxAmount) {
        this(itemStack, component, maxAmount, maxAmount, maxAmount);
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
    public long fillFluid(FluidStack stack, boolean simulate) {
        if (!isValid(stack)) return 0;

        long toReceive = Math.clamp(this.maxAmount - getFluidValue(), 0, Math.min(this.maxFill, stack.getAmount()));
        if (!simulate)
            setFluidStack(FluidStack.create(stack, toReceive));
        return toReceive;
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        if (!isValid(stack)) return 0;

        long toDrain = Math.min(getFluidValue(), Math.min(this.maxDrain, stack.getAmount()));
        if (!simulate)
            setFluidStack(FluidStack.create(stack, getFluidValue() - toDrain));
        return toDrain;
    }

}
