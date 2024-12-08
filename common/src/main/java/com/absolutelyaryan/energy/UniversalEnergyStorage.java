package com.absolutelyaryan.energy;


public interface UniversalEnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    int insert(int amount, boolean simulate);
    int extract(int amount, boolean simulate);
    boolean canInsertEnergy();
    boolean canExtractEnergy();

}
