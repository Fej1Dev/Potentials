package com.fej1fun.potentials.fluid;

import com.fej1fun.potentials.components.FluidAmountMapDataComponent;
import dev.architectury.fluid.FluidStack;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ItemFluidStorage implements UniversalFluidItemStorage {
    protected final long maxAmount;
    protected final long maxFill;
    protected final long maxDrain;
    protected final ItemStack stack;
    protected final DataComponentType<FluidAmountMapDataComponent> component;
    private final int tanks;

    public ItemFluidStorage(DataComponentType<FluidAmountMapDataComponent> component, ItemStack stack, int tanks, long maxAmount, long maxFill, long maxDrain) {
        this.maxAmount = maxAmount;
        this.maxFill = maxFill;
        this.maxDrain = maxDrain;
        this.stack = stack;
        this.component = component;
        this.tanks = tanks;

        if (!this.stack.has(component))
            this.stack.set(component, getEmpty());
    }

    public ItemFluidStorage(DataComponentType<FluidAmountMapDataComponent> component, ItemStack stack, int tanks, long maxAmount) {
        this(component, stack, tanks, maxAmount, maxAmount, maxAmount);
    }

    private FluidAmountMapDataComponent getEmpty() {
        return FluidAmountMapDataComponent.emptyWithSize(this.tanks);
    }

    private FluidAmountMapDataComponent getComponent() {
        return this.stack.getOrDefault(this.component, getEmpty());
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
            if (!simulate) {
                setFluidInTank(i, stack.copyWithAmount(getFluidValueInTank(i) + filled));
                setChanged();
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
            if (getFluidInTank(i).isEmpty()) continue;
            if (getFluidInTank(i).getFluid()!=stack.getFluid()) continue;
            drained = Math.min(getFluidValueInTank(i), Math.min(this.maxDrain, stack.getAmount()));
            if (!simulate) {
                setFluidInTank(i, stack.copyWithAmount(getFluidValueInTank(i) - drained));
                setChanged();
            }

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

    public void setChanged() {
        FluidAmountMapDataComponent oldComponent = this.stack.getOrDefault(this.component, getEmpty());

        Map<Fluid, Long> fluidMap = new HashMap<>();
        for (FluidStack fluidStack : oldComponent.asFluidStackList()) {
            if (!fluidStack.isEmpty()) {
                fluidMap.merge(fluidStack.getFluid(), fluidStack.getAmount(), Long::sum);
            }
        }

        FluidAmountMapDataComponent newComponent = new FluidAmountMapDataComponent(fluidMap);

        this.stack.set(this.component, newComponent);
    }
}
