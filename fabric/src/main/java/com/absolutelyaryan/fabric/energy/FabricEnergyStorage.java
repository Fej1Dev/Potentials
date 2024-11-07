package com.absolutelyaryan.fabric.energy;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public class FabricEnergyStorage implements EnergyStorage {
    final UniversalEnergyStorage universalEnergyStorage;

    public FabricEnergyStorage(UniversalEnergyStorage universalEnergyStorage) {
        this.universalEnergyStorage = universalEnergyStorage;
    }


    @Override
    public boolean supportsInsertion() {
        return universalEnergyStorage.canTakeEnergy();
    }

    @Override
    public long insert(long amount, TransactionContext transaction){
        return universalEnergyStorage.insertValue((int) amount, false);
    }

    @Override
    public  boolean supportsExtraction() {
        return universalEnergyStorage.canGiveEnergy();
    }

    @Override
    public long extract(long amount, TransactionContext transaction){
        return universalEnergyStorage.extractValue((int) amount, false);
    }

    @Override
    public long getAmount(){
        return universalEnergyStorage.getEnergy();
    }

    @Override
    public long getCapacity(){
        return universalEnergyStorage.getMaxEnergy();
    }
}
