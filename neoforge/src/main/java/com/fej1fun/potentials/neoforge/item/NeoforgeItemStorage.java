package com.fej1fun.potentials.neoforge.item;

import com.fej1fun.potentials.item.UniversalItemStorage;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class NeoforgeItemStorage implements IItemHandlerModifiable {

    final UniversalItemStorage itemStorage;

    public NeoforgeItemStorage(final @NotNull UniversalItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    public int getSlots() {
        return itemStorage.getSlots();
    }

    public @NotNull ItemStack getStackInSlot(int slot) {
        return itemStorage.getStackInSlot(slot);
    }

    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return itemStorage.insertItem(slot, stack, simulate);
    }

    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return itemStorage.extractItem(slot, amount, simulate);
    }

    public int getSlotLimit(int slot) {
        return itemStorage.getSlotLimit(slot);
    }

    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return itemStorage.isItemValid(slot, stack);
    }

    @Override
    public void setStackInSlot(int i, ItemStack arg) {

    }
}
