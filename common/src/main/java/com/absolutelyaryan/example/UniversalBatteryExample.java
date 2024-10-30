package com.absolutelyaryan.example;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.minecraft.world.item.Item;

public class UniversalBatteryExample extends Item implements UniversalEnergyStorage {


    public UniversalBatteryExample(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergy() {
        return 20;
    }

    @Override
    public int getMaxEnergy() {
        return 0;
    }

    @Override
    public int takeEnergy(int amount, boolean simulate) {
        return 0;
    }

    @Override
    public int giveEnergy(int amount, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canTakeEnergy() {
        return false;
    }

    @Override
    public boolean canGiveEnergy() {
        return false;
    }
}
