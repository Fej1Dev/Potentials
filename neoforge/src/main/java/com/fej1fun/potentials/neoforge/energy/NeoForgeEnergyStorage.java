package com.fej1fun.potentials.neoforge.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

public class NeoForgeEnergyStorage implements EnergyHandler {
    final UniversalEnergyStorage universalEnergyStorage;

    public NeoForgeEnergyStorage(@NotNull UniversalEnergyStorage universalEnergyStorage) {
       this.universalEnergyStorage = universalEnergyStorage;
    }

    @Override
    public long getAmountAsLong() {
        return universalEnergyStorage.getEnergy();
    }

    @Override
    public long getCapacityAsLong() {
        return universalEnergyStorage.getMaxEnergy();
    }

    @Override
    public int insert(int toReceive, TransactionContext transactionContext) {
        return universalEnergyStorage.insert(toReceive, false);
    }

    @Override
    public int extract(int toReceive, TransactionContext transactionContext) {
       return  universalEnergyStorage.extract(toReceive, false);
    }
}
