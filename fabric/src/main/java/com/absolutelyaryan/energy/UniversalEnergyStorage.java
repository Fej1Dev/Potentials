package com.absolutelyaryan.energy;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public interface UniversalEnergyStorage extends EnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int insertValue(int amount, boolean simulate);
    int extractValue(int amount, boolean simulate);
    boolean canTakeEnergy();
    boolean canGiveEnergy();

    @Override
    default boolean supportsInsertion() {
       return canTakeEnergy();
    }

    @Override
    default long insert(long amount, TransactionContext transaction){
        return insertValue((int) amount, false);
    }

    @Override
    default boolean supportsExtraction() {
        return canGiveEnergy();
    }

    @Override
    default long extract(long amount, TransactionContext transaction){
        return extractValue((int) amount, false);
    }

    @Override
    default long getAmount(){
        return getEnergy();
    }

    @Override
    default long getCapacity(){
        return getMaxEnergy();
    }
}
