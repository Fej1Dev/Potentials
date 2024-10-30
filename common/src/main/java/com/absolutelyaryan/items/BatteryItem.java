package com.absolutelyaryan.items;

import com.absolutelyaryan.energy.ItemEnergyStorage;
import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.providers.EnergyProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BatteryItem extends Item implements EnergyProvider.ITEM {

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public BatteryItem(Properties properties, int capacity, int maxReceive, int maxExtract) {
        super(properties);
    }

    @Override
    public UniversalEnergyStorage getEnergy(ItemStack stack) {
        return new ItemEnergyStorage(stack, capacity, maxReceive, maxExtract);
    }
}

