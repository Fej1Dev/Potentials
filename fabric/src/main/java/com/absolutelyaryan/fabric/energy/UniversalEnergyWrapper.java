package com.absolutelyaryan.fabric.energy;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import team.reborn.energy.api.EnergyStorage;

public class UniversalEnergyWrapper implements UniversalEnergyStorage {
    private final EnergyStorage energyStorage;

    public UniversalEnergyWrapper(EnergyStorage energyStorage) {
        this.energyStorage = energyStorage;
    }

    @Override
    public int getEnergy() {
        return (int) energyStorage.getAmount();
    }

    @Override
    public int getMaxEnergy() {
        return (int) energyStorage.getCapacity();
    }

    @Override
    public int insert(int amount, boolean simulate) {
        if(simulate){
            return energyStorage.getAmount() + amount > energyStorage.getCapacity() ? 0 : amount;
        }
        return (int) energyStorage.insert(amount, Transaction.openOuter());
    }

    @Override
    public int extract(int amount, boolean simulate) {
        if(simulate){
            return energyStorage.getAmount() - amount < 0 ? 0 : amount;
        }
        return (int) energyStorage.extract(amount, Transaction.openOuter());
    }

    @Override
    public boolean canInsertEnergy() {
        return energyStorage.supportsInsertion();
    }

    @Override
    public boolean canExtractEnergy() {
        return energyStorage.supportsExtraction();
    }
}
