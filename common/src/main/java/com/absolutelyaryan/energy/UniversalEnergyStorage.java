package com.absolutelyaryan.energy;

public interface UniversalEnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int insertValue(int amount, boolean simulate);
    int extractValue(int amount, boolean simulate);
    boolean canTakeEnergy();
    boolean canGiveEnergy();

}
