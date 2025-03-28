package com.fej1fun.potentials.providers;

import com.fej1fun.potentials.item.UniversalItemStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemProvider {

    public interface BLOCK {
        @Nullable UniversalItemStorage getFluidTank(@Nullable Direction direction);
    }
    public interface ITEM {
        @Nullable UniversalItemStorage getFluidTank(@NotNull ItemStack stack);
    }
}
