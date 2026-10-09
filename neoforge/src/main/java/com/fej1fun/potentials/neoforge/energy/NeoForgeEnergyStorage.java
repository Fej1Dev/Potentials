package com.fej1fun.potentials.neoforge.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.neoforge.utils.DeferredJournal;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

public class NeoForgeEnergyStorage implements EnergyHandler {
    private final SnapshotJournal<Integer> snapshotJournal = new SnapshotJournal<>() {
        @Override
        protected Integer createSnapshot() {
            return storage.getEnergy();
        }

        @Override
        protected void revertToSnapshot(Integer snapshot) {
            storage.setEnergyStored(snapshot);
        }
    };
    private final DeferredJournal deferredJournal;

    private final UniversalEnergyStorage storage;

    public NeoForgeEnergyStorage(@NotNull UniversalEnergyStorage storage) {
        this.storage = storage;
        this.deferredJournal = DeferredJournal.of(storage);
    }

    @Override
    public long getAmountAsLong() {
        return storage.getEnergy();
    }

    @Override
    public long getCapacityAsLong() {
        return storage.getMaxEnergy();
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
        int amount = storage.insert(toReceive, true);
        if (amount > 0 && !simulate) {
            if (storage.deferUntilCommit()) {
                int deferred = (int) Math.max(Math.min(amount, storage.getMaxEnergy() - storage.getEnergy() - deferredJournal.getPending(null, false)), 0);
                if (deferred > 0)
                    deferredJournal.defer(transactionContext, null, false, deferred, () -> storage.insert(deferred, false));
                return deferred;
            }
            if (!(storage instanceof UniversalIEnergyStorage))
                snapshotJournal.updateSnapshots(transactionContext);
            storage.insert(toReceive, false);
        }
        return amount;
    }

    public int extract(int toExtract, TransactionContext transactionContext, boolean simulate) {
        int amount = storage.extract(toExtract, true);
        if (amount > 0 && !simulate) {
            if (storage.deferUntilCommit()) {
                int deferred = (int) Math.max(Math.min(amount, storage.getEnergy() - deferredJournal.getPending(null, true)), 0);
                if (deferred > 0)
                    deferredJournal.defer(transactionContext, null, true, deferred, () -> storage.extract(deferred, false));
                return deferred;
            }
            if (!(storage instanceof UniversalIEnergyStorage))
                snapshotJournal.updateSnapshots(transactionContext);
            storage.extract(toExtract, false);
        }
        return amount;
    }
}
