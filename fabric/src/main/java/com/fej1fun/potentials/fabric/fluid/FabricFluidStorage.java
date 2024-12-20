package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fabric.utils.SlotStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import java.util.Iterator;

public class FabricFluidStorage implements SlottedStorage<FluidVariant> {
    private final UniversalFluidStorage fluidStorage;

    public FabricFluidStorage(UniversalFluidStorage fluidStorage) {
        this.fluidStorage = fluidStorage;
    }

    @Override
    public int getSlotCount() {
        return fluidStorage.getTanks();
    }

    @Override
    public SingleSlotStorage<FluidVariant> getSlot(int slot) {
        return new SlotStorage<>();
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public Iterator<StorageView<FluidVariant>> iterator() {
        return null;
    }
}
