package com.fej1fun.fabric.energy;

import com.fej1fun.energy.UniversalEnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.jetbrains.annotations.NotNull;
import team.reborn.energy.api.EnergyStorage;

public class FabricEnergyStorage extends SnapshotParticipant<Integer> implements EnergyStorage {
    final UniversalEnergyStorage universalEnergyStorage;

    public FabricEnergyStorage(@NotNull UniversalEnergyStorage universalEnergyStorage) {
        this.universalEnergyStorage = universalEnergyStorage;
    }


    @Override
    public boolean supportsInsertion() {
        return universalEnergyStorage.canInsertEnergy();
    }

    @Override
    public long insert(long amount, TransactionContext transaction){
        if (universalEnergyStorage.insert((int) amount, true) > 0) {
            this.updateSnapshots(transaction);
            return universalEnergyStorage.insert((int) amount, false);
        }
        return 0;
    }

    @Override
    public  boolean supportsExtraction() {
        return universalEnergyStorage.canExtractEnergy();
    }

    @Override
    public long extract(long amount, TransactionContext transaction) {
        if (universalEnergyStorage.extract((int) amount, true) > 0) {
            this.updateSnapshots(transaction);
            return universalEnergyStorage.extract((int) amount, false);
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
    protected void readSnapshot(Integer integer) {
        this.universalEnergyStorage.extract(Integer.MAX_VALUE, false);
        this.universalEnergyStorage.insert(integer, false);
    }

}
