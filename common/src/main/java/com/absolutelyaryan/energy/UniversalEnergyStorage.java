package com.absolutelyaryan.energy;

public interface UniversalEnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int takeEnergy(int amount, boolean simulate);
    int giveEnergy(int amount, boolean simulate);
    boolean canTakeEnergy();
    boolean canGiveEnergy();

}


