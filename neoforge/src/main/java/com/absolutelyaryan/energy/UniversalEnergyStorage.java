package com.absolutelyaryan.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public interface UniversalEnergyStorage extends IEnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int insertValue(int amount, boolean simulate);
    int extractValue(int amount, boolean simulate);
    boolean canTakeEnergy();
    boolean canGiveEnergy();


    @Override
    default int receiveEnergy(int toReceive, boolean simulate){
        return insertValue(toReceive, simulate);
    }
    @Override
    default int extractEnergy(int toExtract, boolean simulate){
        return extractValue(toExtract, simulate);
    }

    @Override
    default int getEnergyStored(){
     return getEnergy();
    }
    @Override
    default int getMaxEnergyStored(){
        return getMaxEnergy();
    }
    @Override
    default boolean canExtract(){
        return canGiveEnergy();
    }
    @Override
    default boolean canReceive(){
        return canTakeEnergy();
    }

}


