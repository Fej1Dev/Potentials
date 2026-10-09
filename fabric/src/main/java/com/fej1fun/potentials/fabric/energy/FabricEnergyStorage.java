package com.fej1fun.potentials.fabric.energy;

import com.fej1fun.potentials.energy.UniversalEnergyStorage;
import com.fej1fun.potentials.fabric.utils.DeferredParticipant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.jetbrains.annotations.NotNull;
import team.reborn.energy.api.EnergyStorage;

public class FabricEnergyStorage extends SnapshotParticipant<Integer> implements EnergyStorage {
    final UniversalEnergyStorage universalEnergyStorage;
    private final DeferredParticipant deferredParticipant;

    public FabricEnergyStorage(@NotNull UniversalEnergyStorage universalEnergyStorage) {
        this.universalEnergyStorage = universalEnergyStorage;
        this.deferredParticipant = DeferredParticipant.of(universalEnergyStorage);
    }

    @Override
    public long insert(long amount, TransactionContext transaction){
        if (universalEnergyStorage.deferUntilCommit()) {
            long pending = this.deferredParticipant.getPending(null, false);
            int inserted = (int) Math.max(Math.min(universalEnergyStorage.insert((int) Math.min(amount, Integer.MAX_VALUE), true), universalEnergyStorage.getMaxEnergy() - universalEnergyStorage.getEnergy() - pending), 0);
            if (inserted > 0)
                this.deferredParticipant.defer(transaction, null, false, inserted, () -> universalEnergyStorage.insert(inserted, false));
            return inserted;
        }
        if (universalEnergyStorage.insert((int) Math.min(amount, Integer.MAX_VALUE), true) > 0) {
            this.updateSnapshots(transaction);
            return universalEnergyStorage.insert((int) Math.min(amount, Integer.MAX_VALUE), false);
        }
        return 0;
    }

    @Override
    public long extract(long amount, TransactionContext transaction) {
        if (universalEnergyStorage.deferUntilCommit()) {
            long pending = this.deferredParticipant.getPending(null, true);
            int extracted = (int) Math.max(Math.min(universalEnergyStorage.extract((int) Math.min(amount, Integer.MAX_VALUE), true), universalEnergyStorage.getEnergy() - pending), 0);
            if (extracted > 0)
                this.deferredParticipant.defer(transaction, null, true, extracted, () -> universalEnergyStorage.extract(extracted, false));
            return extracted;
        }
        if (universalEnergyStorage.extract((int) Math.min(amount, Integer.MAX_VALUE), true) > 0) {
            this.updateSnapshots(transaction);
            return universalEnergyStorage.extract((int) Math.min(amount, Integer.MAX_VALUE), false);
        }
        return 0;
    }

    @Override
    public long getAmount(){
        return universalEnergyStorage.getEnergy();
    }

    @Override
    public long getCapacity(){
        return universalEnergyStorage.getMaxEnergy();
    }

    @Override
    protected Integer createSnapshot() {
        return this.universalEnergyStorage.getEnergy();
    }

    @Override
    public boolean supportsExtraction() {
        return universalEnergyStorage.canExtractEnergy();
    }

    @Override
    public boolean supportsInsertion() {
        return universalEnergyStorage.canInsertEnergy();
    }

    @Override
    protected void readSnapshot(Integer integer) {
        this.universalEnergyStorage.setEnergyStored(integer);
    }

}
