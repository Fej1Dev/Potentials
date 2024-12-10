package com.fej1fun.providers;

import com.fej1fun.fluid.UniversalFluidTank;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidProvider {

    public interface BLOCK {
        @Nullable UniversalFluidTank getFluidTank(@Nullable Direction direction);
    }
    public interface ITEM {
        @Nullable UniversalFluidTank getFluidTank(@NotNull ItemStack stack);
    }
}
