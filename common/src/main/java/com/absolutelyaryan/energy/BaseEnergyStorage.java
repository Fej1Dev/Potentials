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
    public int insert(int toReceive, boolean simulate) {
        if (!canInsertEnergy() || toReceive <= 0) {
            return 0;
        }
        int amount = this.energy + toReceive;
        if (amount > capacity) {
            toReceive = capacity - this.energy;
        }
        if (!simulate) {
            this.energy += toReceive;
        }
        return toReceive;
    }

    @Override
    public int extract(int toExtract, boolean simulate) {
        if (!canExtractEnergy() || toExtract <= 0)
            return 0;
        int amount = this.energy - toExtract;
        if (amount < 0) {
            toExtract = this.energy;
        }
        if (!simulate) {
            this.energy -= toExtract;
        }
        return toExtract;
    }

    @Override
    public boolean canInsertEnergy() {
        return maxExtract > 0;
    }

    @Override
    public boolean canExtractEnergy() {
        return maxReceive > 0;
    }
}
