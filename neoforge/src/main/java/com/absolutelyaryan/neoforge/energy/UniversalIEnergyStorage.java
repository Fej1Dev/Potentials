package com.absolutelyaryan.neoforge.energy;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class UniversalIEnergyStorage implements UniversalEnergyStorage {
    final IEnergyStorage energy;

    public UniversalIEnergyStorage(@NotNull IEnergyStorage energy) {
        this.energy = energy;
    }

    @Override
    public int getEnergy() {
        return energy.getEnergyStored();
    }

    @Override
    public int getMaxEnergy() {
        return energy.getMaxEnergyStored();
    }

    @Override
    public int insert(int amount, boolean simulate) {
        return energy.receiveEnergy(amount, simulate);
    }

    @Override
    public int extract(int amount, boolean simulate) {
        return energy.extractEnergy(amount, simulate);
    }

    @Override
    public boolean canInsertEnergy() {
        return energy.canReceive();
    }

    @Override
    public boolean canExtractEnergy() {
        return energy.canExtract();
    }
}
