package com.absolutelyaryan.fabric.energy;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
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
        return universalEnergyStorage.canTakeEnergy();
    }

    @Override
    public long insert(long amount, TransactionContext transaction){
        if (universalEnergyStorage.insertValue((int) amount, true) > 0) {
            this.updateSnapshots(transaction);
            return universalEnergyStorage.insertValue((int) amount, false);
        }
        return 0;
    }

    @Override
    public  boolean supportsExtraction() {
        return universalEnergyStorage.canGiveEnergy();
    }

    @Override
    public long extract(long amount, TransactionContext transaction) {
        if (universalEnergyStorage.extractValue((int) amount, true) > 0) {
            this.updateSnapshots(transaction);
            return universalEnergyStorage.extractValue((int) amount, false);
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
        this.universalEnergyStorage.extractValue(Integer.MAX_VALUE, false);
        this.universalEnergyStorage.insertValue(integer, false);
    }

}
