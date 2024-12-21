package com.fej1fun.potentials.fluid;

import dev.architectury.fluid.FluidStack;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class ItemFluidStorage implements UniversalFluidStorage {
    private final long maxAmount;
    private final long maxFill;
    private final long maxDrain;
    private final ItemStack stack;
    private final DataComponentType<NonNullList<FluidStack>> component;
    private final int tanks;

    public ItemFluidStorage(DataComponentType<NonNullList<FluidStack>> component, ItemStack stack, int tanks, long maxAmount, long maxFill, long maxDrain) {
        this.maxAmount = maxAmount;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
        this.stack = stack;
        this.component = component;
        this.tanks = tanks;
    }

    public ItemFluidStorage(DataComponentType<NonNullList<FluidStack>> component,int tanks, ItemStack stack, long maxAmount) {
        this(component, stack, tanks, maxAmount, maxAmount, maxAmount);
    }

    private NonNullList<FluidStack> getEmpty() {
        NonNullList<FluidStack> list = NonNullList.create();
        for (int i = 0; i < tanks; i++) {
            list.set(i, FluidStack.empty());
        }
        return list;
    }

    private NonNullList<FluidStack> getFluidStacks() {
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
            if (!(getFluidInTank(i).getFluid()==stack.getFluid() || getFluidInTank(i).isEmpty())) continue;
            if (getFluidInTank(i).getAmount()>=maxAmount) continue;
            filled = Math.clamp(maxAmount - getFluidValueInTank(i), 0L, Math.min(this.maxFill, stack.getAmount()));
            if (!simulate) {
                setFluidInTank(i, FluidStack.create(getFluidInTank(i), getFluidValueInTank(i) + filled));
            }
            break;
        }
        return filled;
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        long drained = 0;
        for (int i = 0; i < getTanks(); i++) {
            if (!isFluidValid(i, stack)) continue;
            if (!(getFluidInTank(i).getFluid()==stack.getFluid() || getFluidInTank(i).isEmpty())) continue;
            drained = Math.min(getFluidValueInTank(i), Math.min(this.maxDrain, stack.getAmount()));
            if (!simulate) {
                setFluidInTank(i, FluidStack.create(getFluidInTank(i), getFluidValueInTank(i) - drained));
            }
            break;
        }
        return FluidStack.create(stack, drained);
    }

    @Override
    public FluidStack drain(int maxAmount, boolean simulate) {
        AtomicReference<FluidStack> toReturn = new AtomicReference<>(FluidStack.empty());
        getFluidStacks().stream().filter(stack -> !stack.isEmpty()).max(Comparator.comparing(FluidStack::getAmount)).ifPresent(stack -> {
            long removedAmount = Math.min(maxAmount, stack.getAmount());
            toReturn.set(FluidStack.create(stack.getFluid(), removedAmount));
            stack.shrink(removedAmount);
        });
        return toReturn.get();
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        return getFluidStacks().iterator();
    }

}
