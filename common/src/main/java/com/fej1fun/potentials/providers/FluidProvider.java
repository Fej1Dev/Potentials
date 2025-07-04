package com.fej1fun.potentials.providers;

import com.fej1fun.potentials.fluid.UniversalFluidItemStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidProvider {

    public interface BLOCK {
        @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction);
    }
    public interface ENTITY {
        @Nullable UniversalFluidStorage getFluidTank(@Nullable Direction direction);
    }
    public interface ITEM {
        @Nullable UniversalFluidItemStorage getFluidTank(@NotNull ItemStack stack);
    }
}
