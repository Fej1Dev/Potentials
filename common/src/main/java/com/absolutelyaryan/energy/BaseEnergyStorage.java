package com.absolutelyaryan.energy;


import net.minecraft.util.Mth;

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

        int inserted = Mth.clamp(this.capacity - this.energy, 0, Math.min(this.maxReceive, toReceive));
        if (!simulate)
            this.energy += inserted;
        return inserted;
    }

    @Override
    public int extract(int toExtract, boolean simulate) {
        if (!canExtractEnergy() || toExtract <= 0)
            return 0;

        int extracted = Math.min(this.energy, Math.min(this.maxExtract, toExtract));
        if (!simulate)
            this.energy -= extracted;
        return extracted;
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
