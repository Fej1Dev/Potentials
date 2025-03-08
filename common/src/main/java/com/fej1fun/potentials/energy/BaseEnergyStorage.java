package com.fej1fun.potentials.energy;

public class BaseEnergyStorage implements UniversalEnergyStorage {
    protected int energy;
    protected final int capacity;
    protected final int maxReceive;
    protected final int maxExtract;

    public BaseEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = 0;
    }

    public BaseEnergyStorage(int capacity) {
        this(capacity, capacity, capacity);
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

    public int insertWithoutLimits(int amount, boolean simulate) {
        int toReceive = Math.clamp(this.capacity - getEnergy(), 0, amount);
        if (!simulate)
            setEnergyStored(getEnergy() + toReceive);

        return toReceive;
    }

    public int extractWithoutLimits(int amount, boolean simulate) {
        int toExtract = Math.min(getEnergy(), amount);
        if (!simulate)
            setEnergyStored(getEnergy() - toExtract);

        return toExtract;
    }

    @Override
    public boolean canInsertEnergy() {
        return maxReceive > 0;
    }

    @Override
    public boolean canExtractEnergy() {
        return maxExtract > 0;
    }
}
