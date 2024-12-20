package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fabric.utils.SlotStorage;
import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public class UniversalFluidVariantStorage implements UniversalFluidStorage {
    private final SlottedStorage<FluidVariant> fluidStorage;

    public UniversalFluidVariantStorage(Storage<FluidVariant> fluidStorage) {
        this.fluidStorage = new SlotStorage<>(fluidStorage);
    }

    @Override
    public int getTanks() {
        return fluidStorage.getSlotCount();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return FluidStackHooksFabric.fromFabric(fluidStorage.getSlot(tank));
    }

    @Override
    public long getTankCapacity(int tank) {
        return fluidStorage.getSlot(tank).getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = fluidStorage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
            if (simulate)
                transaction.abort();
            return inserted;
        }
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long extracted = fluidStorage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
            if (simulate)
                transaction.abort();
            return FluidStack.create(stack, extracted);
        }
    }

    @Override
    public FluidStack drain(int maxAmount, boolean simulate) {
        AtomicReference<FluidStack> toReturn = new AtomicReference<>(FluidStack.empty());
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.getSlots().stream()
                    .filter(StorageView::isResourceBlank)
                    .max(Comparator.comparing(StorageView::getAmount))
                    .ifPresent(storageView -> {
                        long extracted = storageView.extract(storageView.getResource(), maxAmount, transaction);
                        if (simulate)
                            transaction.abort();
                        toReturn.set(FluidStackHooksFabric.fromFabric(storageView.getResource(), extracted));

                    });
            return toReturn.get();
        }
    }
}
