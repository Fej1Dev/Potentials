package com.absolutelyaryan.energy;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

public interface UniversalEnergyStorage extends EnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int takeEnergy(int amount);
    int giveEnergy(int amount);
    boolean canTakeEnergy();
    boolean canGiveEnergy();

    @Override
    default boolean supportsInsertion() {
       return canTakeEnergy();
    }

    @Override
    default long insert(long maxAmount, TransactionContext transaction){
        return takeEnergy((int) maxAmount);
    }

    @Override
    default boolean supportsExtraction() {
        return canGiveEnergy();
    }

    @Override
    default long extract(long maxAmount, TransactionContext transaction){
        return giveEnergy((int) maxAmount);
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
