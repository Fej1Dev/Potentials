package com.absolutelyaryan;


public interface IEnergyStorage {
    int getEnergyStored();
    int getMaxEnergyStored();
    boolean canExtract();
    boolean canReceive();

    int extractEnergy(int maxExtract, boolean simulate);
    int receiveEnergy(int maxReceive, boolean simulate);
}
