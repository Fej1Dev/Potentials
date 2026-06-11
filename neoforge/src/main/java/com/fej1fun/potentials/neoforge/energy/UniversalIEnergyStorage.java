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
        return (int) Math.min(energy.getAmountAsLong(), Integer.MAX_VALUE);
    }

    @Override
    public int getMaxEnergy() {
        return (int) Math.min(energy.getCapacityAsLong(), Integer.MAX_VALUE);
    }

    @Override
    public void setEnergyStored(int amount) {
        try (Transaction tx = Transaction.openRoot()) {
            if (getEnergy() < amount) {
                energy.insert(amount - getEnergy(), tx);
            } else {
                energy.extract(getEnergy() - amount, tx);
            }
            tx.commit();
        }
    }

    @Override
    public int insert(int amount, boolean simulate) {
        try (Transaction tx = Transaction.openRoot()) {
            int toReturn = energy.insert(amount, tx);
            if (simulate) {
                tx.close();
            } else {
                tx.commit();
            }
            return toReturn;
        }
    }

    @Override
    public int extract(int amount, boolean simulate) {
        try (Transaction tx = Transaction.openRoot()) {
            int toReturn = energy.extract(amount, tx);
            if (simulate) {
                tx.close();
            } else {
                tx.commit();
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
