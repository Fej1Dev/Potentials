package com.absolutelyaryan.neoforge;



import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class EnergyCapabilities {

    // Registering block capabilities for energy with support for directions
    public static final BlockCapability<IEnergyStorage, Direction> BLOCK_ENERGY_CAPABILITY =
            BlockCapability.createSided(ModResources.BLOCK_ENERGY_CAPABILITY, IEnergyStorage.class);

    // Registering item capabilities for energy without additional context
    public static final ItemCapability<IEnergyStorage, Void> ITEM_ENERGY_CAPABILITY =
            ItemCapability.createVoid(ModResources.ITEM_ENERGY_CAPABILITY, IEnergyStorage.class);
}