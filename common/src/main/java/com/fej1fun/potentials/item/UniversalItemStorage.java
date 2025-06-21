package com.fej1fun.potentials.item;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * <p> Check {@link net.neoforged.neoforge.items.IItemHandler} for more information about each method.</p>
 * <p> And {@link net.neoforged.neoforge.items.IItemHandlerModifiable} for information about setStackInSlot.</p>
 **/
public interface UniversalItemStorage extends Iterable<ItemStack> {

    int getSlots();
    /**
     * <strong>Taken from Neoforge's IItemHandler:</strong>
     *
     * <p>
     * <strong>IMPORTANT:</strong> This ItemStack <em>MUST NOT</em> be modified. This method is not for
     * altering an inventory's contents. Any implementers who are able to detect
     * modification through this method should throw an exception.
     * </p>
     * <p>
     * <strong><em>SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK</em></strong>
     * </p>
     * **/
    @NotNull ItemStack getStackInSlot(int slot);
    @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate);
    @NotNull ItemStack extractItem(int slot, int amount, boolean simulate);
    int getSlotLimit(int slot);
    boolean isItemValid(int slot, @NotNull ItemStack stack);
    void setStackInSlot(int slot, ItemStack stack);

}
