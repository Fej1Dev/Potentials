package com.absolutelyaryan.fabric.fluid;

import com.absolutelyaryan.fluid.UniversalFluidTank;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.level.material.Fluid;

public class FluidStorageWrapper implements UniversalFluidTank {
    private final Storage<FluidVariant> storage;

    public FluidStorageWrapper(Storage<FluidVariant> storage) {
        this.storage = storage;
    }

    @Override
    public Fluid getBaseFluid() {
        return storage.iterator().next().getResource().getFluid();
    }

    @Override
    public long getFluidValue() {
        return storage.iterator().next().getAmount();
    }

    @Override
    public FluidStack getFluidStack() {
        return FluidStackHooksFabric.fromFabric(storage.iterator().next());
    }

    @Override
    public long getMaxAmount() {
        return storage.iterator().next().getCapacity();
    }

    @Override
    public boolean isValid(FluidStack stack) {
        return storage.iterator().next().getResource().equals(FluidStackHooksFabric.toFabric(stack));
    }

    @Override
    public long fillFluid(FluidStack stack, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
            if (!simulate) transaction.commit();
            return inserted;
        }
    }

    @Override
    public long drainFluid(FluidStack stack, boolean simulate) {
        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = storage.extract(FluidStackHooksFabric.toFabric(stack), stack.getAmount(), transaction);
            if (!simulate) transaction.commit();
            return extracted;
        }
    }

    @Override
    public void setFluidValue(long amount) {
        // Not supported
    }

    @Override
    public void setMaxAmount(long amount) {
        // Not supported
    }
}
