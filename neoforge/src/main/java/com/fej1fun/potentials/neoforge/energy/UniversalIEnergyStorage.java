package com.fej1fun.potentials.neoforge.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public class UniversalIEnergyStorage implements UniversalEnergyStorage {
    final EnergyHandler energy;

    public UniversalIEnergyStorage(@NotNull EnergyHandler energy) {
        this.energy = energy;
    }

    @Override
    public int getEnergy() {
        return energy.getAmountAsInt();
    }

    @Override
    public int getMaxEnergy() {
        return energy.getCapacityAsInt();
    }

    @Override
    public int insert(int amount, boolean simulate) {
        try (Transaction tx = Transaction.open(null)) {
            int toReturn = energy.insert(amount, tx);
            if (!simulate) {
                tx.commit();
            } else {
                tx.close();
            }
            return toReturn;
        }
    }

    @Override
    public int extract(int amount, boolean simulate) {
        try (Transaction tx = Transaction.open(null)) {
            int toReturn = energy.extract(amount, tx);
            if (!simulate) {
                tx.commit();
            } else {
                tx.close();
            }
            return toReturn;
        }
    }

    @Override
    public boolean canInsertEnergy() {
        return getEnergy() < getMaxEnergy();
    }

    @Override
    public boolean canExtractEnergy() {
        return getEnergy() > 0;
    }
}
