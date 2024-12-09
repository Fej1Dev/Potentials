package com.absolutelyaryan.energy;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ItemEnergyStorage implements UniversalEnergyStorage {

    protected ItemStack stack;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;
    protected final DataComponentType<Integer> component;

    public ItemEnergyStorage(ItemStack stack, DataComponentType<Integer> component, int capacity, int maxReceive, int maxExtract) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.component = component;
    }

    public ItemEnergyStorage(ItemStack stack, DataComponentType<Integer> component, int capacity) {
        this(stack, component, capacity, capacity, capacity);
    }

    public void setEnergyStored(int amount) {
        stack.set(component, Math.clamp(amount, 0, getMaxEnergy()));
    }

    @Override
    public boolean canInsertEnergy() {
        return maxExtract > 0;
    }

    @Override
    public boolean canExtractEnergy() {
        return maxReceive > 0;
    }

    @Override
    public int insert(int toReceive, boolean simulate) {
        if (!canInsertEnergy() || toReceive <= 0) {
            return 0;
        }

        int amount = getEnergy() + toReceive;
        if(amount > capacity) {
            toReceive = capacity - getEnergy();
        }
        if (!simulate) {
            setEnergyStored(getEnergy() + toReceive);
        }
        return toReceive;
    }

    @Override
    public int extract(int toExtract, boolean simulate) {
        if (!canExtractEnergy() || toExtract <= 0)
            return 0;
        int amount = getEnergy() - toExtract;
        if (amount < 0) {
            toExtract = getEnergy();
        }
        if (!simulate) {
            setEnergyStored(getEnergy() - toExtract);
        }
        return toExtract;
    }

    @Override
    public int getEnergy() {
        return stack.getOrDefault(component, 0);
    }

    @Override
    public int getMaxEnergy() {
        return capacity;
    }

}
