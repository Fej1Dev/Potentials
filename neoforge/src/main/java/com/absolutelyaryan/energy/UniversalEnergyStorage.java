package com.absolutelyaryan.energy;


import net.neoforged.neoforge.energy.IEnergyStorage;

public interface UniversalEnergyStorage extends IEnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int takeEnergy(int amount);
    int giveEnergy(int amount);
    boolean canTakeEnergy();
    boolean canGiveEnergy();


    @Override
    default int receiveEnergy(int toReceive, boolean simulate){
        return takeEnergy(toReceive);
    }
    @Override
    default int extractEnergy(int toExtract, boolean simulate){
        return giveEnergy(toExtract);
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


