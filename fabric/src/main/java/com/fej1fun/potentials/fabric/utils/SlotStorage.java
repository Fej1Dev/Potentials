package com.fej1fun.potentials.fabric.utils;

import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SlotStorage<T> implements SlottedStorage<T> {
    private final Storage<T> storage;
    private List<StorageView<T>> storageViews = new ArrayList<>();


    public SlotStorage(Storage<T> storage) {
        this.storage = storage;
        for (StorageView<T> storageView : storage) {
            storageViews.add(storageView);
        }
        storageViews = Collections.unmodifiableList(storageViews);
    }

    @Override
    public int getSlotCount() {
        return storageViews.size();
    }

    @Override
    public SingleSlotStorage<T> getSlot(int slot) {
        return (SingleSlotStorage<T>) storageViews.get(slot);
    }

    @Override
    public long insert(T resource, long maxAmount, TransactionContext transaction) {
        return storage.insert(resource, maxAmount, transaction);
    }

    @Override
    public long extract(T resource, long maxAmount, TransactionContext transaction) {
        return storage.extract(resource, maxAmount, transaction);
    }

    @Override
    public @NotNull Iterator<StorageView<T>> iterator() {
        return storageViews.iterator();
    }
}
