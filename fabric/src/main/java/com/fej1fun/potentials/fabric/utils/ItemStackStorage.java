package com.fej1fun.potentials.fabric.utils;

import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.ItemStack;

public class ItemStackStorage extends SingleStackStorage {

    private final ItemStack original;
    private ItemStack stack;

    public ItemStackStorage(ItemStack stack) {
        this.original = stack;
        this.stack = stack;
    }

    @Override
    protected ItemStack getStack() {
        return stack;
    }

    @Override
    protected void setStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    protected void onFinalCommit() {
        if (this.stack == this.original || this.original.isEmpty() || this.original.getItem() != this.stack.getItem())
            return;
        ((PatchedDataComponentMap) this.original.getComponents()).restorePatch(this.stack.getComponentsPatch());
        this.original.setCount(this.stack.getCount());
        this.stack = this.original;
    }
}
