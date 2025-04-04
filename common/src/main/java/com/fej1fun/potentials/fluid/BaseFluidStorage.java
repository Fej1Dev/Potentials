package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.NonNullList;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
* Basic implementation for UniversalFluidStorage
* */
public class BaseFluidStorage implements UniversalFluidStorage {
    private final int tanks;
    protected final long capacity;
    protected final NonNullList<FluidStack> fluidStacks;
    protected final long maxFill;
    protected final long maxDrain;

    public BaseFluidStorage(int tanks, long capacity, long maxFill, long maxDrain) {
        this.tanks = tanks;
        this.capacity = capacity;
        fluidStacks = NonNullList.create();
        for (int i = 0; i < tanks; i++) {
            fluidStacks.add(FluidStack.empty());
        }
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
    }

    public BaseFluidStorage(int tanks, long capacity) {
        this(tanks, capacity, capacity, capacity);
    }

    @Override
    public int getTanks() {
        return tanks;
    }

    /**
     * @return A copy of the FluidStack */
    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluidStacks.get(tank).copy();
    }

    public long getFluidValueInTank(int tank) {
        return fluidStacks.get(tank).getAmount();
    }

    public void setFluidInTank(int tank, FluidStack stack) {
        stack.setAmount(Math.clamp(stack.getAmount(), 0, getTankCapacity(tank)));
        fluidStacks.set(tank, stack);
    }

    @Override
    public long getTankCapacity(int tank) {
        return capacity;
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
            if (!(fluidStacks.get(i).getFluid()==stack.getFluid() || fluidStacks.get(i).isEmpty())) continue;
            if (fluidStacks.get(i).getAmount()>=capacity) continue;
            filled = Math.clamp(this.capacity - getFluidValueInTank(i), 0L, Math.min(this.maxFill, stack.getAmount()));
            if (!simulate)
                fluidStacks.set(i, stack.copyWithAmount(getFluidValueInTank(i) + filled));

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
                setFluidInTank(i, FluidStack.create(getFluidInTank(i), getFluidValueInTank(i) - drained));

            break;
        }
        return FluidStack.create(stack, drained);
    }

    public long fillWithoutLimits(FluidStack stack, boolean simulate) {
        long filled = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (!(fluidStacks.get(i).getFluid()==stack.getFluid() || fluidStacks.get(i).isEmpty())) continue;
            if (fluidStacks.get(i).getAmount()>=capacity) continue;
            filled = Math.clamp(this.capacity - getFluidValueInTank(i), 0L, stack.getAmount());
            if (!simulate)
                fluidStacks.set(i, stack.copyWithAmount(getFluidValueInTank(i) + filled));

            break;
        }
        return filled;
    }

    public FluidStack drainWithoutLimits(FluidStack stack, boolean simulate) {
        long drained = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (getFluidInTank(i).isEmpty()) continue;
            if (getFluidInTank(i).getFluid()!=stack.getFluid()) continue;
            drained = Math.min(getFluidValueInTank(i), stack.getAmount());
            if (!simulate)
                fluidStacks.get(i).shrink(drained);

            break;
        }
        return FluidStack.create(stack, drained);
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        return fluidStacks.iterator();
    }
}
