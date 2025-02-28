package com.fej1fun.potentials.fabric.fluid;

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
import java.util.Iterator;
import java.util.List;

public class UniversalFluidVariantStorage implements UniversalFluidStorage {
    protected final Storage<FluidVariant> storage;

    public UniversalFluidVariantStorage(Storage<FluidVariant> storage) {
        this.storage = storage;
    }

    @Override
    public int getTanks() {
        int i = 0;
        if (storage instanceof SlottedStorage<FluidVariant> slottedStorage)
            return slottedStorage.getSlotCount();
        for (StorageView<FluidVariant> ignored : storage)
            i++;
        return i;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        if (storage instanceof SlottedStorage<FluidVariant> slottedStorage)
            return FluidStackHooksFabric.fromFabric(slottedStorage.getSlot(tank).getResource(), slottedStorage.getSlot(tank).getAmount() / 81L);

        int i = 0;
        for (StorageView<FluidVariant> view : storage) {
            if (i == tank)
                return FluidStackHooksFabric.fromFabric(view.getResource(), view.getAmount() / 81L);
        }

        throw new IndexOutOfBoundsException(tank);
    }

    @Override
    public long getTankCapacity(int tank) {
        if (storage instanceof SlottedStorage<FluidVariant> slottedStorage)
            return slottedStorage.getSlot(tank).getCapacity() / 81L;

        int i = 0;
        for (StorageView<FluidVariant> view : storage) {
            if (i == tank)
                return view.getCapacity() / 81L;
        }

        throw new IndexOutOfBoundsException(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    @Override
    public long fill(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount() * 81L, transaction);
            if (simulate)
                transaction.abort();
            else
                transaction.commit();
            return inserted / 81L;
        }
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long extracted = storage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount() * 81L, transaction);
            if (simulate)
                transaction.abort();
            else
                transaction.commit();
            return FluidStack.create(stack, extracted / 81L);
        }
    }

    @Override
    public @NotNull Iterator<FluidStack> iterator() {
        List<FluidStack> fluidStacks = new ArrayList<>();
        for (StorageView<FluidVariant> storageView : storage) {
            fluidStacks.add(FluidStackHooksFabric.fromFabric(storageView.getResource(), storageView.getAmount() / 81L));
        }
        return fluidStacks.iterator();
    }
}
