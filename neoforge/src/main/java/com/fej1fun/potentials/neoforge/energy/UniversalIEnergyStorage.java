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
        return energy.insert(amount, Transaction.open(null));
    }

    @Override
    public int extract(int amount, boolean simulate) {
        return energy.extract(amount, Transaction.open(null));
    }

    @Override
    public boolean canInsertEnergy() {
        return true;//energy.canReceive(); TODO implement this
    }

    @Override
    public boolean canExtractEnergy() {
        return true;//energy.canExtract(); TODO implement this
    }
}
