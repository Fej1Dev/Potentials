package com.absolutelyaryan.neoforge.energy;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class NeoForgeEnergyStorage implements IEnergyStorage {
    final UniversalEnergyStorage universalEnergyStorage;

    public NeoForgeEnergyStorage(UniversalEnergyStorage universalEnergyStorage) {
        this.universalEnergyStorage = universalEnergyStorage;
    }


    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return universalEnergyStorage.insertValue(toReceive, simulate);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return universalEnergyStorage.extractValue(toExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return universalEnergyStorage.getEnergy();
    }

    @Override
    public int getMaxEnergyStored() {
        return universalEnergyStorage.getMaxEnergy();
    }

    @Override
    public boolean canExtract() {
        return universalEnergyStorage.canGiveEnergy();
    }

    @Override
    public boolean canReceive() {
        return universalEnergyStorage.canTakeEnergy();
    }
}
