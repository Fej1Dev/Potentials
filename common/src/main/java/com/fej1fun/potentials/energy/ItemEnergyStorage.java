package com.fej1fun.potentials.energy;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public class ItemEnergyStorage extends BaseEnergyStorage {

    protected final ItemStack stack;
    protected final DataComponentType<Integer> component;

    public ItemEnergyStorage(final ItemStack stack, DataComponentType<Integer> component, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.stack = stack;
        this.component = component;

        if (!this.stack.has(component))
            stack.set(component, 0);

    }

    public ItemEnergyStorage(ItemStack stack, DataComponentType<Integer> component, int capacity) {
        this(stack, component, capacity, capacity, capacity);
    }

    @Override
    public int getEnergy() {
        return stack.getOrDefault(component, 0);
    }

    @Override
    public void setEnergyStored(int amount) {
        stack.set(component, Math.clamp(amount, 0, getMaxEnergy()));
    }

    @Override
    public int insert(int amount, boolean simulate) {
        if (!canInsertEnergy()) return 0;

        int toReceive = Math.clamp(this.capacity - getEnergy(), 0, Math.min(this.maxReceive, amount));
        if (!simulate)
            setEnergyStored(getEnergy() + toReceive);

        return toReceive;
    }

    @Override
    public int extract(int amount, boolean simulate) {
        if (!canExtractEnergy()) return 0;

        int toExtract = Math.min(getEnergy(), Math.min(this.maxExtract, amount));
        if (!simulate)
            setEnergyStored(getEnergy() - toExtract);

        return toExtract;
    }

    @Override
    public int insertWithoutLimits(int amount, boolean simulate) {
        int toReceive = Math.clamp(this.capacity - getEnergy(), 0, amount);
        if (!simulate)
            setEnergyStored(getEnergy() + toReceive);

        return toReceive;
    }

    @Override
    public int extractWithoutLimits(int amount, boolean simulate) {
        int toExtract = Math.min(getEnergy(), amount);
        if (!simulate)
            setEnergyStored(getEnergy() - toExtract);

        return toExtract;
    }
}
