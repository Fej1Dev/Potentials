package com.absolutelyaryan.energy;

import com.absolutelyaryan.items.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

public class ItemEnergyStorage implements UniversalEnergyStorage {

    protected ItemStack stack;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public ItemEnergyStorage(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
        this.stack = stack;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    /**unchecked, only for use internally*/
    @ApiStatus.Internal
    public void setUncheckedEnergyStored(int amount) {
        stack.set(DataComponents.ENERGY_DATA_COMPONENT.get(), amount);
    }

    public void setEnergyStored(int amount) {
        stack.set(DataComponents.ENERGY_DATA_COMPONENT.get(), Math.clamp(amount, 0, getMaxEnergy()));
    }


    @Override
    public boolean canTakeEnergy() {
        return maxExtract > 0;
    }

    @Override
    public boolean canGiveEnergy() {
        return maxReceive > 0;
    }

    @Override
    public int takeEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(getEnergy(), Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            setEnergyStored(getEnergy() - energyExtracted);
        }
        return energyExtracted;
    }

    @Override
    public int giveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - getEnergy(), Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            setEnergyStored(getEnergy() + energyReceived);
        }
        return energyReceived;
    }

    @Override
    public int getEnergy() {
        return stack.getOrDefault(DataComponents.ENERGY_DATA_COMPONENT.get(), 0);
    }

    @Override
    public int getMaxEnergy() {
        return capacity;
    }


}
