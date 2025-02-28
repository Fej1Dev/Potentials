package com.fej1fun.potentials.fabric.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
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
        try (Transaction transaction = Transaction.openOuter()) {
            int inserted = (int) energyStorage.insert(amount, transaction);
            if (simulate)
                transaction.abort();
            return inserted;
        }
    }

    @Override
    public int extract(int amount, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            int extracted = (int) energyStorage.extract(amount, transaction);
            if (simulate)
                transaction.abort();
            return extracted;
        }
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
