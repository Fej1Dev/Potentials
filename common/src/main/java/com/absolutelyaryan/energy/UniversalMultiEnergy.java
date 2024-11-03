package com.absolutelyaryan.energy;

import java.util.List;

public interface UniversalMultiEnergy {
    List<String> getRegisteredEnergies();
    UniversalEnergyStorage getEnergy(String energyType);
    void addEnergyType(String identifier);

    default int getEnergyValue(String energyType){
        if(getRegisteredEnergies().contains(energyType.toLowerCase())){
            return getEnergy(energyType).getEnergy();
        }
        return 0;
    }
    default int getMaxEnergyValue(String energyType){
        if(getRegisteredEnergies().contains(energyType.toLowerCase())){
            return getEnergy(energyType).getMaxEnergy();
        }
        return 0;
    }
    default int takeEnergy(String energyType, int amount, boolean simulate){
        if(getRegisteredEnergies().contains(energyType.toLowerCase())){
            return getEnergy(energyType).takeEnergy(amount, simulate);
        }
        return 0;
    }
    default int giveEnergy(String energyType, int amount, boolean simulate){
        if(getRegisteredEnergies().contains(energyType.toLowerCase())){
            return getEnergy(energyType).giveEnergy(amount, simulate);
        }
        return 0;
    }
    default boolean canTakeEnergy(String energyType){
        if(getRegisteredEnergies().contains(energyType.toLowerCase())){
            return getEnergy(energyType).canTakeEnergy();
        }
        return false;
    }
    default boolean canGiveEnergy(String energyType){
        if(getRegisteredEnergies().contains(energyType.toLowerCase())){
            return getEnergy(energyType).canGiveEnergy();
        }
        return false;
    }


}
