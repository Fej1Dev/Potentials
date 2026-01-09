package com.fej1fun.potentials.energy;


public interface UniversalEnergyStorage {

    int getEnergy();
    int getMaxEnergy();
    void setEnergyStored(int amount);
    int insert(int amount, boolean simulate);
    int extract(int amount, boolean simulate);
    boolean canInsertEnergy();
    boolean canExtractEnergy();
}
