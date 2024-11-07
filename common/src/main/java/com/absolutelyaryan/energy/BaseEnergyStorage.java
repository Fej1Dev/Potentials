package com.absolutelyaryan.energy;

public class BaseEnergyStorage implements UniversalEnergyStorage {
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public BaseEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = 0;
    }

    public void setEnergyStored(int energy) {
        this.energy = Math.clamp(energy, 0, getMaxEnergy());
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public int getMaxEnergy() {
        return capacity;
    }

    @Override
    public int insertValue(int amount, boolean simulate) {
        int energyExtracted = Math.min(energy, this.maxExtract);
        if (!simulate) {
            energy -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public int extractValue(int amount, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, this.maxReceive);
        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public boolean canTakeEnergy() {
        return maxExtract > 0;
    }

    @Override
    public boolean canGiveEnergy() {
        return maxReceive > 0;
    }
}
