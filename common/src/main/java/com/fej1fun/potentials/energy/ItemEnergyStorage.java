package com.fej1fun.potentials.energy;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public class ItemEnergyStorage implements UniversalEnergyStorage {

    protected final ItemStack stack;
    protected final int capacity;
    protected final int maxReceive;
    protected final int maxExtract;
    protected final DataComponentType<Integer> component;

    public ItemEnergyStorage(final ItemStack stack, DataComponentType<Integer> component, int capacity, int maxReceive, int maxExtract) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
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
    public int getMaxEnergy() {
        return capacity;
    }

    /**
     * This method does not check for max receive and extract, or if energy can be inserted or extracted
     */
    public void setEnergyStored(int amount) {
        stack.set(component, Math.clamp(amount, 0, getMaxEnergy()));
    }

    @Override
    public boolean canInsertEnergy() {
        return maxReceive > 0;
    }

    @Override
    public boolean canExtractEnergy() {
        return maxExtract > 0;
    }

    @Override
    public int insert(int amount, boolean simulate) {
        if (!canInsertEnergy()) return 0;

        int toReceive = Math.clamp(this.capacity - getEnergy(), 0, Math.min(this.maxReceive, amount));
        if (!simulate) {
            setEnergyStored(getEnergy() + toReceive);
            setChanged();
        }

        return toReceive;
    }

    @Override
    public int extract(int amount, boolean simulate) {
        if (!canExtractEnergy()) return 0;

        int toExtract = Math.min(getEnergy(), Math.min(this.maxExtract, amount));
        if (!simulate) {
            setEnergyStored(getEnergy() - toExtract);
            setChanged();
        }


        return toExtract;
    }

    public void setChanged() {
        this.stack.update(this.component, 0, (i) -> getEnergy());
    }
}
