package com.fej1fun.potentials.energy;

import com.fej1fun.potentials.registry.DataComponetRegistry;
import net.minecraft.world.item.ItemStack;

public class ItemEnergyStorage implements UniversalEnergyStorage {

    protected final ItemStack stack;
    protected final int capacity;
    protected final int maxReceive;
    protected final int maxExtract;

    public ItemEnergyStorage(final ItemStack stack, int capacity, int maxReceive, int maxExtract) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;

        if (!this.stack.has(DataComponetRegistry.ENERGY.get()))
            stack.set(DataComponetRegistry.ENERGY.get(), 0);

    }

    public ItemEnergyStorage(ItemStack stack, int capacity) {
        this(stack, capacity, capacity, capacity);
    }

    @Override
    public int getEnergy() {
        return stack.getOrDefault(DataComponetRegistry.ENERGY.get(), 0);
    }

    @Override
    public int getMaxEnergy() {
        return capacity;
    }

    /**
     * This method does not check for max receive and extract, or if energy can be inserted or extracted
     */
    public void setEnergyStored(int amount) {
        stack.set(DataComponetRegistry.ENERGY.get(), Math.clamp(amount, 0, getMaxEnergy()));
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

}
