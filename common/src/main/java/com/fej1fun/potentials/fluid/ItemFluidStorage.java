package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ItemFluidStorage implements UniversalFluidStorage {
    protected final long maxAmount;
    protected final long maxFill;
    protected final long maxDrain;
    protected final ItemStack stack;
    protected final DataComponentType<List<FluidStack>> component;
    private final int tanks;

    public ItemFluidStorage(DataComponentType<List<FluidStack>> component, ItemStack stack, int tanks, long maxAmount, long maxFill, long maxDrain) {
        this.maxAmount = maxAmount;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
        this.stack = stack;
        this.component = component;
        this.tanks = tanks;

        stack.set(component, getEmpty());
    }

    public ItemFluidStorage(DataComponentType<List<FluidStack>> component, ItemStack stack, int tanks, long maxAmount) {
        this(component, stack, tanks, maxAmount, maxAmount, maxAmount);
    }

    private NonNullList<FluidStack> getEmpty() {
        return NonNullList.withSize(tanks, FluidStack.empty());
    }

    private List<FluidStack> getFluidStacks() {
        return stack.getOrDefault(component, getEmpty());
    }

    @Override
    public int getTanks() {
        return tanks;
    }

    /**
     * @return A copy of the FluidStack
     */
    @Override
    public FluidStack getFluidInTank(int tank) {
        return getFluidStacks().get(tank).copy();
    }

    public void setFluidInTank(int tank, FluidStack fluidStack) {
        getFluidStacks().set(tank, fluidStack);
    }

    public long getFluidValueInTank(int tank) {
        return getFluidInTank(tank).getAmount();
    }

    @Override
    public long getTankCapacity(int tank) {
        return maxAmount;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        long filled = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (!(getFluidInTank(i).getFluid() == stack.getFluid() || getFluidInTank(i).isEmpty())) continue;
            if (getFluidValueInTank(i) >= maxAmount) continue;
            filled = Math.clamp(Math.min(this.maxFill, stack.getAmount()), 0L, maxAmount - getFluidValueInTank(i));
            if (!simulate)
                setFluidInTank(i, stack.copyWithAmount(getFluidValueInTank(i) + filled));

            break;
        }
        return filled;
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        long drained = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (getFluidInTank(i).isEmpty()) continue;
            if (getFluidInTank(i).getFluid()!=stack.getFluid()) continue;
            drained = Math.min(getFluidValueInTank(i), Math.min(this.maxDrain, stack.getAmount()));
            if (!simulate)
                setFluidInTank(i, stack.copyWithAmount(getFluidValueInTank(i) - drained));

            break;
        }
        return FluidStack.create(stack, drained);
    }

    @Override
    public FluidStack drain(long maxAmount, boolean simulate) {
        AtomicReference<FluidStack> toReturn = new AtomicReference<>(FluidStack.empty());
        getFluidStacks().stream().filter(stack -> !stack.isEmpty()).max(Comparator.comparing(FluidStack::getAmount)).ifPresent(stack -> {
            long removedAmount = Math.min(maxAmount, stack.getAmount());
            toReturn.set(FluidStack.create(stack.getFluid(), removedAmount));
            if (!simulate)
                stack.shrink(removedAmount);
        });
        return toReturn.get();
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        return getFluidStacks().iterator();
    }

}
