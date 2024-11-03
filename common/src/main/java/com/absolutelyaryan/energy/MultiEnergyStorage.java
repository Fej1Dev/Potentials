package com.absolutelyaryan.energy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiEnergyStorage implements UniversalMultiEnergy {
    private final HashMap<String, UniversalEnergyStorage> energyMap = new HashMap<>();

    @Override
    public List<String> getRegisteredEnergies() {
        return new ArrayList<>(energyMap.keySet());
    }

    @Override
    public UniversalEnergyStorage getEnergy(String energyType) {
        return energyMap.get(energyType);
    }

    @Override
    public void addEnergyType(String identifier) {
        energyMap.put(identifier, new BaseEnergyStorage(1024, 1024, 1024));
    }
}
