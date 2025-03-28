package com.fej1fun.potentials.neoforge.item;

import com.fej1fun.potentials.item.UniversalItemStorage;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class UniversalIItemHandler implements UniversalItemStorage {

    final IItemHandler itemHandler;

    public UniversalIItemHandler(@NotNull IItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }

    @Override
    public int getSlots() {
        return itemHandler.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return itemHandler.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return itemHandler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return itemHandler.isItemValid(slot, stack);
    }
}
