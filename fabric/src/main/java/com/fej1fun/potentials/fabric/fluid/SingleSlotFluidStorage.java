package com.fej1fun.potentials.fabric.fluid;

import com.fej1fun.potentials.fluid.UniversalFluidStorage;
import dev.architectury.hooks.fluid.fabric.FluidStackHooksFabric;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class SingleSlotFluidStorage implements SingleSlotStorage<FluidVariant> {
    private final UniversalFluidStorage fluidStorage;
    private final int slot;

    public SingleSlotFluidStorage(UniversalFluidStorage fluidStorage, int slot) {
        this.fluidStorage = fluidStorage;
        this.slot = slot;
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        return fluidStorage.fill(FluidStackHooksFabric.fromFabric(resource, maxAmount / 81L), false) * 81L;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        return fluidStorage.drain(FluidStackHooksFabric.fromFabric(resource, maxAmount / 81L), false).getAmount() * 81L;
    }

    @Override
    public boolean isResourceBlank() {
        return fluidStorage.getFluidInTank(slot).isEmpty();
    }

    @Override
    public FluidVariant getResource() {
        return FluidStackHooksFabric.toFabric(fluidStorage.getFluidInTank(slot));
    }

    @Override
    public long getAmount() {
        return fluidStorage.getFluidInTank(slot).getAmount()*81;
    }

    @Override
    public long getCapacity() {
        return fluidStorage.getTankCapacity(slot)*81;
    }
}
