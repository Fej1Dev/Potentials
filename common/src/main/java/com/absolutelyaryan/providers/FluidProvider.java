package com.absolutelyaryan.providers;

import com.absolutelyaryan.fluid.UniversalFluidTank;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FluidProvider {
    public interface BLOCK {
        UniversalFluidTank getFluidTank(@Nullable Direction direction);
    }
    public interface ITEM {
        UniversalFluidTank getFluidTank(ItemStack stack);
    }
}
