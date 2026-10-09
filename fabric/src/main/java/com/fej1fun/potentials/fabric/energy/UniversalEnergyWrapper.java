package com.fej1fun.potentials.fabric.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.utils.FabricTransactionHelper;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import team.reborn.energy.api.EnergyStorage;

public class UniversalEnergyWrapper implements UniversalEnergyStorage {
    private final EnergyStorage energyStorage;

    public UniversalEnergyWrapper(EnergyStorage energyStorage) {
        this.energyStorage = energyStorage;
    }

    @Override
    public int getEnergy() {
        return (int) Math.min(energyStorage.getAmount(), Integer.MAX_VALUE);
    }

    @Override
    public int getMaxEnergy() {
        return (int) Math.min(energyStorage.getCapacity(), Integer.MAX_VALUE);
    }

    @Override
    public void setEnergyStored(int amount) {
        if (getEnergy() < amount) {
            insert(amount - getEnergy(), false);
        } else {
            extract(getEnergy() - amount, false);
        }
    }

    @Override
    public int insert(int amount, boolean simulate) {
        try (Transaction transaction = FabricTransactionHelper.open()) {
            long inserted = energyStorage.insert(amount, transaction);

            if (simulate)
                transaction.close();
            else
                transaction.commit();

            return (int)inserted;
        }
    }

    @Override
    public int extract(int amount, boolean simulate) {
        try (Transaction transaction = FabricTransactionHelper.open()) {
            long extracted = energyStorage.extract(amount, transaction);

            if (simulate)
                transaction.close();
            else
                transaction.commit();

            return (int)extracted;
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
