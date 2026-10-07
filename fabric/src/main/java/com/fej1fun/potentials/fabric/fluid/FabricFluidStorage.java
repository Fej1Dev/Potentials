package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fluid.FluidSnapshots;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FabricFluidStorage extends SnapshotParticipant<List<FluidStack>> implements SlottedStorage<FluidVariant> {
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
        return new SingleSlotFluidStorage(fluidStorage, slot);
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return fluidStorage.fill(FluidStackHooksFabric.fromFabric(resource, maxAmount / 81L), false) * 81L;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return fluidStorage.drain(FluidStackHooksFabric.fromFabric(resource, maxAmount / 81L), false).getAmount() * 81L;
    }

    @Override
    public Iterator<StorageView<FluidVariant>> iterator() {
        List<StorageView<FluidVariant>> toReturn = new ArrayList<>();
        for (int i = 0; i < getSlotCount(); i++) {
            toReturn.add(new SingleSlotFluidStorage(fluidStorage, i));
        }
        return toReturn.iterator();
    }

    @Override
    protected List<FluidStack> createSnapshot() {
        return FluidSnapshots.take(fluidStorage);
    }

    @Override
    protected void readSnapshot(List<FluidStack> snapshot) {
        FluidSnapshots.restore(fluidStorage, snapshot);
    }
}
