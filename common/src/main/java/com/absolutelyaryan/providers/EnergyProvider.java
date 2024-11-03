package com.absolutelyaryan.providers;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import com.absolutelyaryan.energy.UniversalMultiEnergy;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EnergyProvider {

    public interface BLOCK {
        UniversalEnergyStorage getEnergy(@Nullable Direction direction);
        UniversalEnergyStorage getEnergy(String energyType, @Nullable Direction direction);
    }
    public interface ITEM {
        UniversalEnergyStorage getEnergy(ItemStack stack);
        UniversalEnergyStorage getEnergy(String energyType, ItemStack itemStack);
    }
}
