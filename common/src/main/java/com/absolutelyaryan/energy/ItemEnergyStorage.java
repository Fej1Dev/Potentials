package com.absolutelyaryan.energy;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

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

    /**unchecked, only for use internally*/
    @ApiStatus.Internal
    public void setUncheckedEnergyStored(int amount) {
        stack.set(component, amount);
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
    public int insert(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(getEnergy(), Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            setEnergyStored(getEnergy() - energyExtracted);
        }
        return energyExtracted;
    }

    @Override
    public int extract(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - getEnergy(), Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            setEnergyStored(getEnergy() + energyReceived);
        }
        return energyReceived;
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
