package com.absolutelyaryan.energy;

import com.absolutelyaryan.items.DataComponents;
import com.absolutelyaryan.objects.IEnergyStorage;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

public class ItemEnergyStorage implements IEnergyStorage {

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

    @Override
    public int getEnergyStored() {
        return stack.getOrDefault(DataComponents.ENERGY_DATA_COMPONENT.get(), 0);
    }

    /**unchecked, only for use internally*/
    @ApiStatus.Internal
    public void setUncheckedEnergyStored(int amount) {
        stack.set(DataComponents.ENERGY_DATA_COMPONENT.get(), amount);
    }

    public void setEnergyStored(int amount) {
        stack.set(DataComponents.ENERGY_DATA_COMPONENT.get(), Math.clamp(amount, 0 ,getMaxEnergyStored()));
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(getEnergyStored(), Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            setEnergyStored(getEnergyStored() - energyExtracted);
        }
        return energyExtracted;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - getEnergyStored(), Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            setEnergyStored(getEnergyStored() + energyReceived);
        }
        return energyReceived;
    }

}
