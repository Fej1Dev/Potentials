package com.absolutelyaryan;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import dev.architectury.platform.Platform;

public class BatteryItem extends Item implements IEnergyStorage {
    private final BaseEnergyStorage energyStorage;

    public BatteryItem(Properties properties, int capacity, int maxReceive, int maxExtract) {
        super(properties);
        this.energyStorage = new BaseEnergyStorage(capacity, maxReceive, maxExtract);
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return energyStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return energyStorage.canReceive();
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }
}

