package com.fej1fun.potentials.item;

import static com.fej1fun.potentials.Potentials.LOG;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BaseItemStorage implements UniversalItemStorage {
    private final NonNullList<ItemStack> items;
    private final int slotLimit;

    public BaseItemStorage(int slots, int slotLimit) {
        this.items = NonNullList.withSize(slots, ItemStack.EMPTY);
        this.slotLimit = slotLimit;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        ItemStack slotStack = getStackInSlot(slot);
        int slotCount = slotStack.getCount();

        if (!isItemValid(slot, stack)) return stack;
        if (slotCount >= getSlotLimit(slot)) return stack;
        if (!slotStack.isEmpty() && slotStack.getItem() != stack.getItem()) return stack;

        int inserted = Math.min(getSlotLimit(slot) - slotCount, stack.getCount());

        if (!simulate)
            add(slot, inserted);

        return stack.copyWithCount(stack.getCount() + inserted);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack slotStack = getStackInSlot(slot);

        if (slotStack.isEmpty()) return ItemStack.EMPTY;

        int extracted = Math.min(slotStack.getCount(), amount);
        if (!simulate)
            add(slot, -extracted);

        return slotStack.copyWithCount(extracted);
    }

    @Override
    public int getSlotLimit(int slot) {
        return slotLimit;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull Iterator<ItemStack> iterator() {
        return items.iterator();
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        items.set(slot, stack);
    }

    protected void set(int slot, int amount) {
        items.set(slot, getStackInSlot(slot).copyWithCount(amount));
    }

    protected void add(int slot, int amount) {
        int afterAddition = getStackInSlot(slot).getCount() + amount;
        if (afterAddition < 0 || afterAddition > getSlotLimit(slot)) {
            LOG.error("Amount: {} will result in a stack size bigger than the slot limit, or smaller than 0.", amount);
            LOG.error("Clamping amount to fit limits");
            afterAddition = Math.clamp(afterAddition, 0, getSlotLimit(slot));
        }

        setStackInSlot(slot, getStackInSlot(slot).copyWithCount(afterAddition));
    }
}
