package com.absolutelyaryan.neoforge.energy;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class NeoForgeEnergyStorage implements IEnergyStorage {
    final UniversalEnergyStorage universalEnergyStorage;

    public NeoForgeEnergyStorage(@NotNull UniversalEnergyStorage universalEnergyStorage) {
       this.universalEnergyStorage = universalEnergyStorage;
    }



    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return universalEnergyStorage.insert(toReceive, simulate);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return universalEnergyStorage.extract(toExtract, simulate);
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
        return universalEnergyStorage.canExtractEnergy();
    }

    @Override
    public boolean canReceive() {
        return universalEnergyStorage.canInsertEnergy();
    }

    public UniversalEnergyStorage getUniversalEnergyStorage() {
        return universalEnergyStorage;
    }
}
