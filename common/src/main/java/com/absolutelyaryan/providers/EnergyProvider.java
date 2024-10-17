package com.absolutelyaryan.providers;

import com.absolutelyaryan.objects.IEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EnergyProvider {

    public interface BLOCK {
        IEnergyStorage getEnergy(@Nullable Direction direction);
    }
    public interface ITEM {
        IEnergyStorage getEnergy(ItemStack stack);
    }
}
