package com.fej1fun.potentials.fluid;

import com.fej1fun.potentials.components.FluidAmountMapDataComponent;
import com.fej1fun.potentials.registry.DataComponetRegistry;
import dev.architectury.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ItemFluidStorage implements UniversalFluidItemStorage {
    protected final long maxAmount;
    protected final long maxFill;
    protected final long maxDrain;
    protected final ItemStack stack;
    private final int tanks;

    public ItemFluidStorage(ItemStack stack, int tanks, long maxAmount, long maxFill, long maxDrain) {
        this.maxAmount = maxAmount;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
        this.stack = stack;
        this.tanks = tanks;

        if (!this.stack.has(DataComponetRegistry.FLUID_LIST.get()))
            this.stack.set(DataComponetRegistry.FLUID_LIST.get(), getEmpty());
    }

    public ItemFluidStorage(ItemStack stack, int tanks, long maxAmount) {
        this(stack, tanks, maxAmount, maxAmount, maxAmount);
    }

    private FluidAmountMapDataComponent getEmpty() {
        return FluidAmountMapDataComponent.emptyWithSize(this.tanks);
    }

    private FluidAmountMapDataComponent getComponent() {
        return this.stack.getOrDefault(DataComponetRegistry.FLUID_LIST.get(), getEmpty());
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
        return getComponent().getAsFluidStack(tank);
    }

    public void setFluidInTank(int tank, FluidStack fluidStack) {
        getComponent().setFluidStack(tank, fluidStack);
    }

    public long getFluidValueInTank(int tank) {
        return getComponent().getAmount(tank);
    }

    @Override
    public long getTankCapacity(int tank) {
        return this.maxAmount;
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
            if (getFluidValueInTank(i) >= this.maxAmount) continue;
            filled = Math.clamp(Math.min(this.maxFill, stack.getAmount()), 0L, this.maxAmount - getFluidValueInTank(i));
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
    public @NotNull Iterator<FluidStack> iterator() {
        return getComponent().asFluidStackList().iterator();
    }

    @Override
    public ItemStack getContainer() {
        return this.stack;
    }
}
