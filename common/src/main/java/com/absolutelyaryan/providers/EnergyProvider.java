package com.absolutelyaryan.providers;

import com.absolutelyaryan.energy.UniversalEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EnergyProvider {

    public interface BLOCK {
        UniversalEnergyStorage getEnergy(@Nullable Direction direction);
    }
    public interface ITEM {
        UniversalEnergyStorage getEnergy(ItemStack stack);
    }
}
