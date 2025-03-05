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
            i++;
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
            i++;
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
            long amount = 0L;
            for (int i = 0; i < getTanks(); i++) {
                if ((getFluidInTank(i).isEmpty() || getFluidInTank(i).isFluidEqual(stack)) && isFluidValid(i, stack) && getFluidInTank(i).getAmount() < getTankCapacity(i) ) {
                    amount = Math.min(getTankCapacity(i) - getFluidInTank(i).getAmount(), stack.getAmount());
                    break;
                }
            }

            amount = storage.insert(FluidStackHooksFabric.toFabric(stack), amount * 81L, transaction) / 81L;
            if (simulate || amount == 0L)
                transaction.close();
            else {
                transaction.commit();
            }

            return amount;
        }
    }

    @Override
    public FluidStack drain(FluidStack stack, boolean simulate) {
        try(Transaction transaction = Transaction.openOuter()) {
            long amount = 0L;
            for (int i = 0; i < getTanks(); i++) {
                if ((getFluidInTank(i).isEmpty() || getFluidInTank(i).isFluidEqual(stack)) && isFluidValid(i, stack)) {
                    amount = Math.min(getFluidInTank(i).getAmount(), stack.getAmount());
                    break;
                }
            }

            amount = storage.extract(FluidStackHooksFabric.toFabric(stack), amount * 81L, transaction) / 81L;
            if (simulate || amount == 0L)
                transaction.close();
            else {
                transaction.commit();
            }

            return FluidStack.create(stack, amount);
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
