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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
        return FluidStackHooksFabric.fromFabric(fluidStorage.getSlot(tank).getResource(), fluidStorage.getSlot(tank).getAmount() / 81L);
    }

    @Override
    public long getTankCapacity(int tank) {
        return fluidStorage.getSlot(tank).getCapacity() / 81L;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = fluidStorage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount() * 81L, transaction);
            if (simulate)
                transaction.abort();
            return inserted / 81L;
        }
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long extracted = fluidStorage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount() * 81L, transaction);
            if (simulate)
                transaction.abort();
            return FluidStack.create(stack, extracted / 81L);
        }
    }

    @Override
    public FluidStack drain(long maxAmount, boolean simulate) {
        AtomicReference<FluidStack> toReturn = new AtomicReference<>(FluidStack.empty());
        try(Transaction transaction = Transaction.openOuter()) {
            fluidStorage.getSlots().stream()
                    .filter(StorageView::isResourceBlank)
                    .max(Comparator.comparing(StorageView::getAmount))
                    .ifPresent(storageView -> {
                        long extracted = storageView.extract(storageView.getResource(), maxAmount * 81L, transaction);
                        if (simulate)
                            transaction.abort();
                        toReturn.set(FluidStackHooksFabric.fromFabric(storageView.getResource(), extracted / 81L));

                    });
            return toReturn.get();
        }
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        List<FluidStack> fluidStacks = new ArrayList<>();
        for (StorageView<FluidVariant> storageView : fluidStorage) {
            fluidStacks.add(FluidStackHooksFabric.fromFabric(storageView.getResource(), storageView.getAmount() / 81L));
        }
        return fluidStacks.iterator();
    }
}
