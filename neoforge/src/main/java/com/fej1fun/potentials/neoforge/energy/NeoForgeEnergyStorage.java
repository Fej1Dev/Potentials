package com.fej1fun.potentials.neoforge.energy;

import com.fej1fun.potentials.energy.BaseEnergyStorage;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

public class NeoForgeEnergyStorage implements EnergyHandler {
    private final SnapshotJournal<Integer> snapshotJournal = new SnapshotJournal<>() {
        @Override
        protected Integer createSnapshot() {
            return baseEnergyStorage.getEnergy();
        }

        @Override
        protected void revertToSnapshot(Integer snapshot) {
            baseEnergyStorage.setEnergyStored(snapshot);
        }
    };

    private final BaseEnergyStorage baseEnergyStorage;

    public NeoForgeEnergyStorage(@NotNull BaseEnergyStorage baseEnergyStorage) {
        this.baseEnergyStorage = baseEnergyStorage;
    }

    @Override
    public long getAmountAsLong() {
        return baseEnergyStorage.getEnergy();
    }

    @Override
    public long getCapacityAsLong() {
        return baseEnergyStorage.getMaxEnergy();
    }

    @Override
    public int insert(int toReceive, TransactionContext transactionContext) {
        return insert(toReceive, transactionContext, false);
    }

    @Override
    public int extract(int toExtract, TransactionContext transactionContext) {
        return extract(toExtract, transactionContext, false);
    }

    public int insert(int toReceive, TransactionContext transactionContext, boolean simulate) {
        int amount = baseEnergyStorage.insert(toReceive, simulate);
        if (amount > 0 && !simulate) {
            snapshotJournal.updateSnapshots(transactionContext);
        }

        return amount;
    }

    public int extract(int toExtract, TransactionContext transactionContext, boolean simulate) {
        int amount = baseEnergyStorage.extract(toExtract, simulate);
        if (amount > 0 && !simulate) {
            snapshotJournal.updateSnapshots(transactionContext);
        }

        return amount;
    }
}
