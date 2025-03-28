package com.fej1fun.potentials.item;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Check {@link net.neoforged.neoforge.items.IItemHandler} for more information about each method
 * */
public interface UniversalItemStorage {
    int getSlots();
    @NotNull ItemStack getStackInSlot(int slot);
    @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate);
    @NotNull ItemStack extractItem(int slot, int amount, boolean simulate);
    int getSlotLimit(int slot);
    boolean isItemValid(int slot, @NotNull ItemStack stack);
}
